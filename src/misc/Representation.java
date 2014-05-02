package misc;

import java.awt.Color;

public class Representation {
	public char ASCII;
	public Color foreground;
	public Color background;

	protected boolean modifiable = true;

	public Representation(char ASCII) {
		this.ASCII = ASCII;
		defaultColors();
		// TODO Auto-generated constructor stub
	}

	public Representation(char ASCII, boolean isModifiable) {
		this.ASCII = ASCII;
		this.modifiable = isModifiable;
		defaultColors();
		// TODO Auto-generated constructor stub
	}

	private void defaultColors() {
		foreground = Color.white;
		background = Color.black;
	}

	public boolean isModifiable() {
		return modifiable;
	}

	public boolean setASCII(char ASCII) {
		if (modifiable) {
			this.ASCII = ASCII;
			return true;
		}
		return false;
	}

	public boolean setForeground(Color foreground) {
		if (modifiable) {
			this.foreground = foreground;
			return true;
		}
		return false;
	}

	public boolean setBackground(Color background) {
		if (modifiable) {
			this.background = background;
			return true;
		}
		return false;
	}

	public boolean setColor(Color foreground, Color background) {
		if (modifiable) {
			this.foreground = foreground;
			this.background = background;
			return true;
		}
		return false;
	}

	public boolean set(char ASCII, Color foreground, Color background) {
		if (modifiable) {
			this.ASCII = ASCII;
			this.foreground = foreground;
			this.background = background;
			return true;
		}
		return false;
	}
}
