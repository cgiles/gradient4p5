
package gradient4p5.gradient;

import java.util.ArrayList;
import gradient4p5.gradient.colorgradient.*;
import processing.core.*;
import processing.data.IntList;

/**
 * Gradient : The backbone of this library.
 *
 * 
 */

public class Gradient implements PConstants {
	public ArrayList<ColorGradient> shades;
	private PApplet parent;
	private float lowerKey;
	private float higherKey;
	private IntList shadesToDelete;

	/**
	 * 
	 * <p>
	 * Gradient myGradient=new Gradient(THIS);
	 * <p>
	 * 
	 * @param myParent
	 *            a PApplet reference, most of the this THIS
	 */
	public Gradient(PApplet myParent) {
		parent = myParent;
		shades = new ArrayList<ColorGradient>();
		lowerKey = 1.0f;
		higherKey = 0.0f;
		shadesToDelete = new IntList();
		parent.registerMethod("post", this);

	}

	/**
	 * 
	 * 
	 * Attempt to add a shade to the gradient, if a shade already exists with the
	 * same key, return false
	 * <p>
	 * myGradient.addShadeToGradient(0.3,color(126,23,59));
	 * <p>
	 * 
	 * @param key
	 *            a float between 0.0 and 1.0
	 * @param value
	 *            a color
	 * @return
	 */
	public boolean addShadeToGradient(float key, int value) {
		for (ColorGradient sh : shades) {
			if (sh.key == key)
				return false;
		}
		ColorGradient nSh = new ColorGradient(key, value);
		setLowerKey(key);
		setHigherKey(key);
		shades.add(nSh);
		if (getNumberShades() > 1)
			sortShades();
		return true;
	}

	/**
	 * 
	 * 
	 * Reset the Gradient
	 * <p>
	 * myGradient.clear();
	 * <p>
	 */
	public void clear() {
		shades.clear();

	}

	/**
	 *
	 * val return the interpolate color between two shade or the only shade if there
	 * is only one
	 * <p>
	 * color aColor=myGradient.getGradientShade(0.66);
	 * <p>
	 * 
	 * @param val
	 *            a float between 0.0 and 1.0
	 * @return an interpolate color
	 */
	public int getGradientShade(float val) {
		if (getNumberShades() == 0)
			return -1;
		int result = 0;
		val = parent.constrain(val, lowerKey, higherKey);
		if (shades.size() == 1) {
			result = shades.get(0).value;
		} else if (shades.size() == 2) {
			float amount = parent.map(val, lowerKey, higherKey, 0.0f, 1.0f);
			result = parent.lerpColor(shades.get(0).value, shades.get(1).value, amount);
		} else {
			for (int i = 0; i < shades.size() - 1; i++) {
				if (val >= shades.get(i).key && val < shades.get(i + 1).key) {
					float amount = parent.map(val, shades.get(i).key, shades.get(i + 1).key, 0.0f, 1.0f);
					result = parent.lerpColor(shades.get(i).value, shades.get(i + 1).value, amount);
					return result;
				}
			}

		}
		if (val == lowerKey)
			result = shades.get(0).value;
		if (val == higherKey)
			result = shades.get(shades.size() - 1).value;
		return result;
	}

	/**
	 * 
	 * <p>
	 * float aFloat = myGradient.getHigherKey();
	 * <p>
	 * 
	 * @return the higher key of the gradient
	 */
	public float getHigherKey() {
		return higherKey;
	}

	/**
	 * 
	 * <p>
	 * float anArrayF[]=myGradient.getKeysArray();
	 * <p>
	 * 
	 * @return return an array of float containing the keys value
	 */
	public float[] getKeysArray() {
		float keys[] = new float[getNumberShades()];
		for (int i = 0; i < keys.length; i++) {
			keys[i] = shades.get(i).key;
		}
		return keys;
	}

	/**
	 * <p>
	 * float aFloat=myGradient.getLowerKey()
	 * <p>
	 * 
	 * @return the lower key of the gradient
	 */

	public float getLowerKey() {
		return lowerKey;
	}

	/**
	 * <p>
	 * myGradient.getNumberShades();
	 * <p>
	 * 
	 * @return the number of shades composing your gradient
	 */
	public int getNumberShades() {
		return shades.size();
	}

	/**
	 * 
	 * <p>
	 * color anArrayC[]=myGradient.getValuesArray();
	 * <p>
	 * 
	 * @return return an array of color containing the color of the shades
	 */
	public int[] getValuesArray() {
		int[] values = new int[getNumberShades()];
		for (int i = 0; i < values.length; i++) {
			values[i] = shades.get(i).value;
		}
		return values;
	}

	/**
	 * 
	 * 
	 * init a simple gradient of two colors, black and white
	 * <p>
	 * myGradient.initBW();
	 * <p>
	 * 
	 */

	public void initBW() {
		shades.clear();
		addShadeToGradient(0.0f, parent.color(0));
		addShadeToGradient(1.0f, parent.color(255));

	}

	/**
	 * 
	 * init a HSB gradient
	 * <p>
	 * myGradient.initHSB();
	 * <p>
	 * 
	 */
	public void initHSB() {
		parent.pushStyle();
		parent.colorMode(parent.HSB, 100);
		for (int i = 0; i <= 100; i += 10) {
			int colHSB = parent.color(i, 100, 100);
			float ratio = parent.map(i, 0f, 100f, 0.0f, 1.0f);
			addShadeToGradient(ratio, colHSB);
		}
		parent.popStyle();
	}

	/**
	 * 
	 * 
	 * private function, simply compare cg0 and cg1 keys, two ColorGradient. if
	 * cg0's key is lower than cg1's key, return true
	 * 
	 * 
	 * @param cg0
	 *            ColorGradient
	 * @param cg1
	 *            ColorGradient
	 * @return cg0.key<cg1.key
	 */
	private boolean isLower(ColorGradient cg0, ColorGradient cg1) {
		boolean result = false;
		if (cg0.key < cg1.key)
			result = true;
		return result;
	}

	public void post() {
		if (shadesToDelete.size() > 0) {
			if (getNumberShades() == 1) {
				clear();
				shadesToDelete.clear();
				lowerKey = 1.0f;
				higherKey = 0.0f;
			}
			else {
				shadesToDelete.sort();
				shadesToDelete.reverse();
				for (int i = 0; i < shadesToDelete.size(); i++) {
					shades.remove(shadesToDelete.get(i));
				}
				sortShades();
				shadesToDelete.clear();
				
			}
		}
		lowerKey = 1.0f;
		higherKey = 0.0f;
		for(int i=0;i<getNumberShades();i++) {
			setLowerKey(getKeysArray()[i]);
			setHigherKey(getKeysArray()[i]);
			}
	}

	/**
	 * Remove a shade of the gradient, index must be between 0 and the number of
	 * shades minus 1
	 * <p>
	 * myGradient.removeByIndex(5);
	 * <p>
	 * 
	 * @param index
	 *            the index of the shade to remove of the gradient
	 */

	public void removeByIndex(int index) {
		if (index < getNumberShades() && getNumberShades() > 0) {
			shadesToDelete.append(index);
		}
	}

	/**
	 * 
	 * 
	 * Private function
	 * 
	 * Set the higher key value compare nHigherKey to higherKey and replace this one
	 * by nHigherKey if it is higher than lowerkey
	 * 
	 * @param nLowerKey
	 * @return lowerKey
	 */
	private float setHigherKey(float nHigherKey) {
		if (nHigherKey > higherKey)
			higherKey = nHigherKey;
		return higherKey;
	}

	/**
	 * 
	 * 
	 * Private function
	 * 
	 * Compare nLowerKey to lowerKey and replace this one by nLowerKey if it is
	 * smaller than lowerkey
	 * 
	 * @param nLowerKey
	 * @return lowerKey
	 */
	private float setLowerKey(float nLowerKey) {

		if (nLowerKey < lowerKey)
			lowerKey = nLowerKey;
		return lowerKey;
	}

	/**
	 * 
	 * 
	 * Private function
	 * 
	 * simply sort shades, use isLower()
	 * 
	 */

	private void sortShades() {
		for (int i = shades.size() - 2; i >= 0; i--) {
			if (!isLower(shades.get(i), shades.get(i + 1))) {
				ColorGradient nSh = new ColorGradient(shades.get(i + 1).key, shades.get(i + 1).value);
				shades.remove(i + 1);
				shades.add(i, nSh);

			}
		}
	}

}