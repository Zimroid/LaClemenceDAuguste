<?php require_once("view/siteGlobalVar.php"); ?>
<h2>Règles</h2>
(<a class="blue" href="<?php echo $sitePath; ?>/ressources/ReglesAuguste.pdf">Télécharger les règles complètes de <b><?php echo $siteName; ?></b></a>)

<h3>Déroulement d'un tour :</h3>
Phase 1 :
<ul>
<li>déplacement simultané des légionnaires (en ramassant éventuellement des armures)</li>	
<li>prise en compte des collisions éventuelles</li>
</ul>

Phase 2 :
<ul>
<li>déplacement du Laurier si quelqu'un l'a demandé</li> 
</ul>

Phase 3 :
<ul>	
<li>prise en compte des tenailles</li> 	
<li>prise en compte  des combats frontaux</li> 	
</ul>
<br>

La première équipe qui ramène le Laurier au fond de l'un de ses campements gagne la partie.
<br>

<h3>Les déplacements</h3>
Les légionnaires d'une même légion (couleur) collés les uns aux autres forment un groupe.
À chaque tour, un légionnaire peut être déplacé par légion. Celui-ci peut se déplacer sur n'importe quelle case avec laquelle, lui ou un légionnaire de son groupe est en contact.
Un légionnaire n'a pas le droit d'aller sur une case déjà occupée par un légionnaire, même si vous pensez que ce légionnaire va bouger immédiatement. De même, on n'a pas le droit d'aller sur une case occupée par la couronne de lauriers.
<br>

<h3>Les collisions</h3>
Puisque l'ensemble des légionnaires (un par légion) effectuent leur déplacement en simultané à chaque tour, il est possible de rencontrer une collision.
Si deux légionnaires arrivent sur la même case, ils sont détruits. Si cette case contenait une armure, elle est également détruite.
Les armures ne servent à rien en cas de collision. Si la collision a lieu entre une légion et le laurier, le laurier survit.
<br>

<h3>Les armures</h3>
Lorsqu'un légionnaire ramasse une armure, elle est immédiatement efficace pour les combats du tour.
Un légionnaire équipé d'une armure ne peut aller sur une case disposant d'une autre armure.
<br>

<h3>Les combats</h3>
Les armures fournissent un point supplémentaire en cas de combat si la légion possédant l'armure est en première ligne.
Les combats frontaux sont pris en compte simultanément.
Les tenailles seront prises en compte simultanément, mais avant les combats frontaux, si des légionnaires sont détruits par une tenaille, le combat frontal n'a pas lieu.
Les boucliers (au sol) ou la couronne de laurier ne protègent pas des tenailles.
Une fois que l'on a enlevé les morts, on passe au tour suivant : on ne reconsidère pas les combats frontaux (qui ont pu être changés par le retrait de pions).
</p>