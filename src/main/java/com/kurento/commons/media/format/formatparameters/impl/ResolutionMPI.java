package com.kurento.commons.media.format.formatparameters.impl;

public class ResolutionMPI {

	private PictureSize pictureSize;
	private int mpi;
	private int width;
	private int height;

	public PictureSize getPictureSize() {
		return pictureSize;
	}

	public void setPictureSize(PictureSize pictureSize) {
		this.pictureSize = pictureSize;
	}

	public int getMpi() {
		return mpi;
	}

	public void setMpi(int mpi) {
		this.mpi = mpi;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public ResolutionMPI(PictureSize pictureSize, int mpi) {
		this.pictureSize = pictureSize;
		this.mpi = mpi;
		this.width = pictureSize.getWidth();
		this.height = pictureSize.getHeight();
	}

	public String toString() {
		if (PictureSize.CUSTOM.equals(pictureSize))
			return pictureSize + "=" + width + "," + height + "," + mpi;
		else
			return pictureSize + "=" + mpi;
	}

}
