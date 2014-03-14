package clientjavaacrobatt;

public class XY
{
	private static double sqrt3=Math.sqrt(3);
	public double x;
	public double y;

	public XY(double xx, double yy) {
		x = xx;
		y = yy;
	}

	//for serialization
	public XY(){}

	public UW toUW(int boardSize,int RBoard){

		double Rin=boardSize/(4*RBoard+2);
		double Rout=  Rin*2/sqrt3;
		double Ux=3./2*Rout;
		double Uy=-Rin;
		double Wx=0.;
		double Wy=2*Rin;

		double xp=x-boardSize/2.;
		double yp=y-boardSize/2.;

		double det= Ux*Wy-Uy*Wx;

		double up=( Wy*xp-Wx*yp)/det;
		double wp=(-Uy * xp+Ux*yp)/det;

		UW candidat=new UW( (int) Math.round (up),(int) Math.round(wp));
		
		double disSq =  this.distSquare(candidat.toXY(boardSize, RBoard));

		if (disSq<Rin*Rin) return candidat;
		else return new UW (1000,1000);	       
	}

	public double distSquare(XY ab){		
		return (x-ab.x)*(x-ab.x) + (y-ab.y)*(y-ab.y);
	}
	//	
	//	public UW toUW(int boardDim,int RBoard){
	//		
	//		double sqrt3=Math.sqrt(3);
	//		double Rin=boardDim/(4*RBoard+2);
	//		
	//		double xp=x-boardDim/2;
	//		double yp=y-boardDim/2;
	//		
	//		double projW= yp/2/Rin;
	//		int w=(int) Math.round(projW);
	//		
	//		
	//		double projU = (sqrt3/2*xp-yp/2)/2/Rin;
	//		int u= (int) Math.round(projU);
	//		
	//		double projV = (-sqrt3/2*xp-yp/2)/2/Rin;
	//		int v= (int) Math.round(projV);
	//		
	//	
	////		
	////		if (projV-projU-projW>1./2 ) {u++;w++; }
	////		else if (projV-projU-projW<-1./2){u--;w--;}
	//		
	//		return new UW(0,w);
	//	}

	@Override
	public String toString() {
		return "XY [x=" + x + ", y=" + y + "]";
	}
}