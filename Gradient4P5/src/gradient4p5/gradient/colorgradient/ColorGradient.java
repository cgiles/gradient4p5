package gradient4p5.gradient.colorgradient;

import processing.core.*;
/**Base of the library, just a pair of value, the key, a float, and the value, an int/Color
 * 
 * @author Gilles
 *
 */

public class ColorGradient {
	public float key;
	public int value;
/**Constructor
 * 
 * @param key a float between 0.0 and 1.0
 * @param value a color
 */
	public ColorGradient(float key, int value) {
		this.key = key;
		this.value = value;
	} 

}