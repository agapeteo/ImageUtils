package info.tvir.imageutils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public interface IImageResizer {

	/**
	 * save scaled image
	 * @param originalImage image to use for scale
	 * @param marginWidth margin width
	 * @param marginHeight margin height
	 * @param fileToSave file to save
	 * @throws IOException
	 */
	public abstract void saveImageByMargins(BufferedImage originalImage,
			int marginWidth, int marginHeight, File fileToSave)
			throws IOException;

	/**
	 * save scaled image from image bytes
	 * @param originalImageBytes image bytes of image to scale
	 * @param marginWidth margin width
	 * @param marginHeight margin height
	 * @param fileToSave file to save
	 * @throws IOException
	 */
	public abstract void saveImageByMargins(byte[] originalImageBytes,
			int marginWidth, int marginHeight, File fileToSave)
			throws IOException;

	/**
	 * save scaled image from image file
	 * @param originalFile image file to scale
 	 * @param marginWidth margin width
	 * @param marginHeight margin height
	 * @param fileToSave file to save
	 * @throws IOException
	 */
	public abstract void saveImageByMargins(File originalFile, int marginWidth,
			int marginHeight, File fileToSave) throws IOException;

	/**
	 * save scaled image from image InputStream
	 * @param imageInputStream image InputStream to scale
 	 * @param marginWidth margin width
	 * @param marginHeight margin height
	 * @param fileToSave file to save
	 * @throws IOException
	 */
	public abstract void saveImageByMargins(InputStream imageInputStream,
			int marginWidth, int marginHeight, File fileToSave)
			throws IOException;

	/**
	 * save scaled image from original image
	 * @param originalImage original image
	 * @param marginDimension margin Dimensions
	 * @param fileToSave file to save
	 * @throws IOException
	 */
	public abstract void saveImageByMargins(BufferedImage originalImage,
			Dimension marginDimension, File fileToSave) throws IOException;

	/**
	 * gets target dimension that fits margins.
	 * By default, if original image is smaller than margins 
	 *  target dimensions WILL NOT scale to margins size, but use original size.
	 *  If you want scale (in this case) to margins use scaleToMax(true)
	 * @param marginWidth
	 * @param marginHeight
	 * @param originalWidth
	 * @param originalHeight
	 * @return target dimension of image to scale
	 */
	public abstract Dimension getTargetDimensionByMargins(int marginWidth,
			int marginHeight, int originalWidth, int originalHeight);

	/**
	 * gets target dimension that fits margins.
	 * By default, if original image is smaller than margins 
	 *  target dimensions WILL NOT scale to margins size, but use original size.
	 *  If you want scale (in this case) to margins use scaleToMax(true) 
	 * @param marginsDimension margin dimension of image
	 * @param originalDimension original dimension of image
	 * @return target dimension of image to scale
	 */
	public abstract Dimension getTargetDimensionByMargins(
			Dimension marginsDimension, Dimension originalDimension);

	/**
	 * get scaled image. Default image type is BufferedImage.TYPE_INT_RGB.
	 * Use setImageType() to change it
	 * @param image to scale
	 * @param targetWidth target image width
	 * @param targetHeight target image height
	 * @return scaled image 
	 */
	public abstract BufferedImage getScaledImage(Image image, int targetWidth,
			int targetHeight);

	/**
	 * get image from InputStream.
	 * @param input ImputStream to get image
	 * @return image from InputStream
	 * @throws IOException
	 */
	public abstract BufferedImage getBufferedImage(InputStream input)
			throws IOException;

	/**
	 * get image from file
	 * @param fileImage file to get image
	 * @return image from file
	 * @throws IOException
	 */
	public abstract BufferedImage getBufferedImage(File fileImage)
			throws IOException;

	/**
	 * get image from bytes array
	 * @param imageBytes bytes array to get image
	 * @return image from bytes array
	 * @throws IOException
	 */
	public abstract BufferedImage getBufferedImage(byte[] imageBytes)
			throws IOException;

	/**
	 * saves image to file
	 * @param image image to save
	 * @param file file that will be used to save image
	 * @throws IOException
	 */
	public abstract void saveImageToFile(BufferedImage image, File file)
			throws IOException;

	/**
	 * By default, if original image is smaller than margins 
	 *  target dimensions WILL NOT scale to margins size, but use original size.
	 *  If you want scale (in this case) to margins use scaleToMax(true) 
	 * default is false
	 * @param scaleToMax
	 */
	public abstract void setScaleToMax(boolean scaleToMax);

	/**
	 * get boolean value if image will be scaled to max. value of margin
	 * @return
	 */
	public abstract boolean isScaleToMax();

	/**
	 * set format of image, default is "JPG" 
	 * @param imageFormat image format to save (JPG, PNG...)
	 */
	public abstract void setImageFormat(String imageFormat);

	/**
	 * get image format
	 * @return image format (jpg, png...)
	 */
	public abstract String getImageFormat();

	/**
	 * type of image. default is BufferedImage.TYPE_INT_RGB
	 * @return type of image
	 */
	public abstract int getImageType();

	/**
	 * set image type (default is BufferedImage.TYPE_INT_RGB)
	 * @param imageType
	 */
	public abstract void setImageType(int imageType);	

}