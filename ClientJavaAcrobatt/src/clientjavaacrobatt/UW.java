package clientjavaacrobatt;

public class UW
{
	public int u;
	public int w;
	/**this third coordinate is redundant. It just facilite the rotation*/
	private int v;

	public UW(int uu,int ww){
		u=uu;
		w=ww;
		v=0;
	}

	//for serialization
	public UW(){}
	
	public XY toXY(int boardSize,int RBoard)
        {
		// this function will be called very often, so we avoid the evaluation of sqrt(3)
		
		double Rin=boardSize/(4*RBoard+2);
		double Rout=  Rin*2/Math.sqrt(3); //Rin*2/1.73205080757;
		double Ux=3./2*Rout;
		double Uy=-Rin;
		double Wx=0.;
		double Wy=2*Rin;
                
		double x=(u*Ux+w*Wx)+boardSize/2;
		double y=(u*Uy+w*Wy)+boardSize/2;
		return new XY(x,y);
	}

	public void reduce(){
		u=u-v;
		w=w-v;
		v=0;
	}

	@Override
	public String toString() {
		return  u + "," + w;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + u;
		result = prime * result + w;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UW other = (UW) obj;
		if (u != other.u)
			return false;
		if (w != other.w)
			return false;
		return true;
	}

	public void rotate(int i){
		int up,vp,wp;		
		i=i%6;
		switch (i){
		case 0: 
		break;
		case 1: wp=-u;up=-v;vp=-w;u=up;v=vp;w=wp;reduce();
		break;
		case 2: up=w;wp=v;vp=u;u=up;v=vp;w=wp;reduce();
		break;
		case 3: u=-u;w=-w;
		break;
		case 4: wp=u;up=v;vp=w;u=up;v=vp;w=wp;reduce();
		break;
		case 5: vp=-u;wp=-v;up=-w;u=up;v=vp;w=wp;reduce();		
		}
	}
}