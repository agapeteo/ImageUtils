package info.tvir.imageutils;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ImageResizer implements IImageResizer {
	private boolean scaleToMax;	
	private String imageFormat;
	private int imageType;
		
	public ImageResizer(){				
		setScaleToMax(false);		
		setImageFormat("JPG");
		setImageType(BufferedImage.TYPE_INT_RGB);		
	}
	
	/* (non-Javadoc)
	 * @see info.tvir.imageutils.IImageResizer#saveImageByMargins(java.awt.image.BufferedImage, int, int, java.io.File)
	 */
	@Override
	public void saveImageByMargins(
			BufferedImage originalImage, 
			int marginWidth, int marginHeight, 
			File fileToSave) throws IOException{
		
		int originalWidth = originalImage.getWidth();
		int originalHeight = originalImage.getHeight();
		
		Dimension targetDimension = 
			getTargetDimensionByMargins(
					marginWidth, marginHeight,
					originalWidth, originalHeight);
		
		BufferedImage scaledImage = 
			getScaledImage( originalImage, 
					targetDimension.getWidth(), targetDimension.getHeight() );
		
		saveImageToFile(scaledImage, fileToSave);
	}	
	
	/* (non-Javadoc)
	 * @see info.tvir.imageutils.IImageResizer#saveImageByMargins(byte[], int, int, java.io.File)
	 */
	@Override
	public void saveImageByMargins(
			byte[] originalImageBytes, 
			int marginWidth, int marginHeight, 
			File fileToSave) throws IOException{		
		BufferedImage originalImage = getBufferedImage(originalImageBytes);		
		saveImageByMargins(originalImage, marginWidth, marginHeight, fileToSave);		
	}	
	
	/* (non-Javadoc)
	 * @see info.tvir.imageutils.IImageResizer#saveImageByMargins(java.io.File, int, int, java.io.File)
	 */
	@Override
	public void saveImageByMargins(
			File originalFile, 
			int marginWidth, int marginHeight, 
			File fileToSave) throws IOException{
		BufferedImage originalImage = getBufferedImage(originalFile);		
		saveImageByMargins(originalImage, marginWidth, marginHeight, fileToSave);
	}	
	
	/* (non-Javadoc)
	 * @see info.tvir.imageutils.IImageResizer#saveImageByMargins(java.io.InputStream, int, int, java.io.File)
	 */
	@Override
	public void saveImageByMargins (
			InputStream imageInputStream, 
			int marginWidth, int marginHeight, 
			File fileToSave) throws IOException{
		BufferedImage originalImage = getBufferedImage(imageInputStream);		
		saveImageByMargins(originalImage, marginWidth, marginHeight, fileToSave);
	}	
		
	/* (non-Javadoc)
	 * @see info.tvir.imageutils.IImageResizer#saveImageByMargins(java.awt.image.BufferedImage, info.tvir.imageutils.ImageResizer.Dimension, java.io.File)
	 */
	@Override
	public void saveImageByMargins(
			BufferedImage originalImage, 
			Dimension marginDimension, 
			File fileToSave)throws IOException{
		
		saveImageByMargins(
				originalImage, 
				marginDimension.getWidth(), 
				marginDimension.getHeight(), 
				fileToSave);
	}	
	
	/* (non-Javadoc)
	 * @see info.tvir.imageutils.IImageResizer#getTargetDimensionByMargins(int, int, int, int)
	 */
	@Override
	public Dimension getTargetDimensionByMargins(
			int marginWidth, int marginHeight,
			int originalWidth, int originalHeight){		
		
		// cast int to double for correct calculations
		double marginWidthDouble = new Integer(marginWidth).doubleValue();
		double marginHeightDouble = new Integer(marginHeight).doubleValue();
		double originalWidthDouble = new Integer(originalWidth).doubleValue();
		double originalHeightDouble = new Integer(originalHeight).doubleValue();
		
		boolean fitByWidth;
		double marginValueDouble;
		double marginRatio = marginWidthDouble/marginHeightDouble;
		double originalRatio = originalWidthDouble/originalHeightDouble;
		
		// calculate tagetValueDouble and which side to fit
		if (marginWidthDouble < marginHeightDouble){//vertical target													
			if (marginRatio > originalRatio){
				marginValueDouble = marginHeightDouble;
				fitByWidth = false;
			} else {
				marginValueDouble = marginWidthDouble;
				fitByWidth = true;
			}			
		} else { // horizontal target or square			
			if (marginRatio < originalRatio){
				marginValueDouble = marginWidthDouble;
				fitByWidth = true;
			} else {
				marginValueDouble = marginHeightDouble;
				fitByWidth = false;
			}
		}
				
		return fitToMargin(fitByWidth, marginValueDouble, originalWidthDouble, originalHeightDouble);
	}
	
	private Dimension fitToMargin(boolean fitByWidth,
			double marginValueDouble,
			double originalWidthDouble, double originalHeightDouble){
		
		double targetWidthDouble;
		double targetHeightDouble;
		
		if (fitByWidth){
			if (scaleToMax){
				targetWidthDouble = marginValueDouble;
			} else {
				if (originalWidthDouble < marginValueDouble){
					targetWidthDouble = originalWidthDouble;
				} else {
					targetWidthDouble = marginValueDouble;
				}
			}			
			targetHeightDouble = targetWidthDouble/originalWidthDouble * originalHeightDouble; 
		} else { // fit by height
			if (scaleToMax){
				targetHeightDouble = marginValueDouble;
			} else {
				if (originalHeightDouble < marginValueDouble){
					targetHeightDouble = originalHeightDouble;
				} else {
					targetHeightDouble = marginValueDouble;
				}
			}
			
			targetWidthDouble = targetHeightDouble/originalHeightDouble * originalWidthDouble;
		}
		
		int targetWidth = new Double(targetWidthDouble).intValue();
		int targetHeight = new Double(targetHeightDouble).intValue();
		
		return new Dimension(targetWidth, targetHeight);
	}
	
	
	
	/* (non-Javadoc)
	 * @see info.tvir.imageutils.IImageResizer#getImageDimensionByMargins(info.tvir.imageutils.ImageResizer.Dimension, info.tvir.imageutils.ImageResizer.Dimension)
	 */
	@Override
	public Dimension getTargetDimensionByMargins (
			Dimension marginsDimension, 
			Dimension originalDimension){
		return getTargetDimensionByMargins(
				marginsDimension.getWidth(), 
				marginsDimension.getHeight(),
				originalDimension.getWidth(), 
				originalDimension.getHeight());
	}
	
	/* (non-Javadoc)
	 * @see info.tvir.imageutils.IImageResizer#getScaledImage(java.awt.Image, int, int)
	 */
	@Override
	public BufferedImage getScaledImage(
			Image image, 
			int targetWidth, int targetHeight){
				
		BufferedImage result = new BufferedImage(targetWidth, targetHeight, imageType);   
		Graphics2D g = result.createGraphics();  
		g.setComposite(AlphaComposite.Src);  
		g.drawImage(image, 0, 0, targetWidth, targetHeight, null);  
		g.dispose();  
		return result;
	}
	
	/* (non-Javadoc)
	 * @see info.tvir.imageutils.IImageResizer#getBufferedImage(java.io.InputStream)
	 */
	@Override
	public BufferedImage getBufferedImage(InputStream input) throws IOException{		
			return ImageIO.read(input);
	}
	
	/* (non-Javadoc)
	 * @see info.tvir.imageutils.IImageResizer#getBufferedImage(java.io.File)
	 */
	@Override
	public BufferedImage getBufferedImage(File fileImage) throws IOException{
		return ImageIO.read(fileImage);
	}
	
	/* (non-Javadoc)
	 * @see info.tvir.imageutils.IImageResizer#getBufferedImage(byte[])
	 */
	@Override
	public BufferedImage getBufferedImage(byte[] imageBytes) throws IOException {
		InputStream input = new ByteArrayInputStream(imageBytes);
		return getBufferedImage(input);
	}	
	
	/* (non-Javadoc)
	 * @see info.tvir.imageutils.IImageResizer#saveImageToFile(java.awt.image.BufferedImage, java.io.File)
	 */
	@Override
	public void saveImageToFile(
			BufferedImage image, File file) throws IOException{		
		ImageIO.write(	image, imageFormat, file);		
	}	
	
	/* (non-Javadoc)
	 * @see info.tvir.imageutils.IImageResizer#setScaleToMax(boolean)
	 */
	@Override
	public void setScaleToMax(boolean scaleToMax) {
		this.scaleToMax = scaleToMax;
	}

	/* (non-Javadoc)
	 * @see info.tvir.imageutils.IImageResizer#isScaleToMax()
	 */
	@Override
	public boolean isScaleToMax() {
		return scaleToMax;
	}

	/* (non-Javadoc)
	 * @see info.tvir.imageutils.IImageResizer#setImageFormat(java.lang.String)
	 */
	@Override
	public void setImageFormat(String imageFormat) {
		this.imageFormat = imageFormat;
	}

	/* (non-Javadoc)
	 * @see info.tvir.imageutils.IImageResizer#getImageFormat()
	 */
	@Override
	public String getImageFormat() {
		return imageFormat;
	}

	/* (non-Javadoc)
	 * @see info.tvir.imageutils.IImageResizer#getImageType()
	 */
	@Override
	public int getImageType() {
		return imageType;
	}

	/* (non-Javadoc)
	 * @see info.tvir.imageutils.IImageResizer#setImageType(int)
	 */
	@Override
	public void setImageType(int imageType) {
		this.imageType = imageType;
	}	

}
