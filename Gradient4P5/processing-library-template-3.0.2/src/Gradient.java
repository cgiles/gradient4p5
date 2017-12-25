
package gradient4p5.gradient;

import java.util.ArrayList;
import gradient4p5.gradient.colorgradient.*;
import processing.core.*;

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

	/**
	 * 
	 * @param myParent
	 */
	public Gradient(PApplet myParent) {
		parent = myParent;
		shades = new ArrayList<ColorGradient>();
		lowerKey = 1.0f;
		higherKey = 0.0f;
	}

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

	public void clear() {
		shades.clear();

	}

	public int getGradientShade(float val) {
		int result = 0;
		val = parent.constrain(val, lowerKey, higherKey);
		if (shades.size() == 1) {
			result = shades.get(0).value;
		} else if (shades.size() == 2) {
			float amount = parent.map(val, lowerKey, higherKey, 0.0f, 1.1f);
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

	public float getHigherKey() {
		return higherKey;
	}

	public float[] getKeysArray() {
		float keys[] = new float[getNumberShades()];
		for (int i = 0; i < keys.length; i++) {
			keys[i] = shades.get(i).key;
		}
		return keys;
	}

	public float getLowerKey() {
		return lowerKey;
	}

	public int getNumberShades() {
		return shades.size();
	}

	public int[] getValuesArray() {
		int[] values = new int[getNumberShades()];
		for (int i = 0; i < values.length; i++) {
			values[i] = shades.get(i).value;
		}
		return values;
	}

	public void initBW() {
		shades.clear();
		addShadeToGradient(0.0f, parent.color(0));
		addShadeToGradient(1.0f, parent.color(255));

	}

	public void initHSB() {
		parent.pushStyle();
		parent.colorMode(parent.HSB, 100);
		for (int i = 0; i < 100; i += 10) {
			int colHSB = parent.color(i, 100, 100);
			float ratio = parent.map(i, 0f, 100f, 0.0f, 1.0f);
			addShadeToGradient(ratio, colHSB);
		}
		parent.popStyle();
	}

	private boolean isLower(ColorGradient cg0, ColorGradient cg1) {
		boolean result = false;
		if (cg0.key < cg1.key)
			result = true;
		return result;
	}

	/**
	 * Set the lower key value Compare nLowerKey to lowerKey and replace this one by
	 * nLowerKey if it is smaller than lowerkey
	 * 
	 * @param nLowerKey
	 * @return lowerKey
	 */
	private float setLowerKey(float nLowerKey) {

		if (nLowerKey < lowerKey)
			lowerKey = nLowerKey;
		return lowerKey;
	}

	private float setHigherKey(float nHigherKey) {
		if (nHigherKey > higherKey)
			higherKey = nHigherKey;
		return higherKey;
	}

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