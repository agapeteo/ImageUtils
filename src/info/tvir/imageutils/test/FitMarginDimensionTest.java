package info.tvir.imageutils.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.tvir.imageutils.Dimension;
import info.tvir.imageutils.IImageResizer;
import info.tvir.imageutils.ImageResizer;

public class FitMarginDimensionTest {
	private Logger log = LoggerFactory.getLogger(getClass());
	
	private IImageResizer imageResizer;
	private Dimension marginDimension;
	private Dimension originalDimension;
	private Dimension expected;
	
	private Dimension result;

	@Before
	public void init(){
		imageResizer = new ImageResizer();
	}
	
	@Test
	public void testEqual(){
		marginDimension = new Dimension(100, 200);
		originalDimension = new Dimension(100, 200);
		expected = new Dimension(100, 200);		
		result = imageResizer.getTargetDimensionByMargins(marginDimension , originalDimension);		
		log.info("margin: "+marginDimension+", originals: "+originalDimension+", result is: " + result);
		assertEquals(expected, result);
	}
	
	@Test
	public void testVertical(){
		marginDimension = new Dimension(100, 200);
		originalDimension = new Dimension(200, 400);
		expected = new Dimension(100, 200);		
		result = imageResizer.getTargetDimensionByMargins(marginDimension , originalDimension);		
		log.info("margin: "+marginDimension+", originals: "+originalDimension+", result is: " + result);
		assertEquals(expected, result);
	}
	
	@Test
	public void testHorizontal(){
		marginDimension = new Dimension(200, 100);
		originalDimension = new Dimension(400, 200);
		expected = new Dimension(200, 100);		
		result = imageResizer.getTargetDimensionByMargins(marginDimension , originalDimension);		
		log.info("margin: "+marginDimension+", originals: "+originalDimension+", result is: " + result);
		assertEquals(expected, result);
	}
	
	@Test
	public void testVMarrinHOrig(){
		marginDimension = new Dimension(100, 200);
		originalDimension = new Dimension(400, 200);
		expected = new Dimension(100, 50);		
		result = imageResizer.getTargetDimensionByMargins(marginDimension , originalDimension);		
		log.info("margin: "+marginDimension+", originals: "+originalDimension+", result is: " + result);
		assertEquals(expected, result);		
	}
	
	@Test
	public void testHMarrinVOrig(){ 
		marginDimension = new Dimension(200, 100);
		originalDimension = new Dimension(200, 400);
		expected = new Dimension(50, 100);	
		result = imageResizer.getTargetDimensionByMargins(marginDimension , originalDimension);		
		log.info("margin: "+marginDimension+", originals: "+originalDimension+", result is: " + result);
		assertEquals(expected, result);
	}
	
	@Test
	public void testSquare(){ 
		marginDimension = new Dimension(100, 100);
		originalDimension = new Dimension(400, 400);
		expected = new Dimension(100, 100);		
		result = imageResizer.getTargetDimensionByMargins(marginDimension , originalDimension);		
		log.info("margin: "+marginDimension+", originals: "+originalDimension+", result is: " + result);
		assertEquals(expected, result);
	}
	
	@Test
	public void testVOriginSmaller(){
		marginDimension = new Dimension(400, 400);
		originalDimension = new Dimension(100, 200);
		expected = new Dimension(100, 200);		
		result = imageResizer.getTargetDimensionByMargins(marginDimension , originalDimension);		
		log.info("margin: "+marginDimension+", originals: "+originalDimension+", result is: " + result);
		assertEquals(expected, result);
	}
	
	@Test
	public void testHOriginSmaller(){
		marginDimension = new Dimension(400, 400);
		originalDimension = new Dimension(200, 100);
		expected = new Dimension(200, 100);		
		result = imageResizer.getTargetDimensionByMargins(marginDimension , originalDimension);		
		log.info("margin: "+marginDimension+", originals: "+originalDimension+", result is: " + result);
		assertEquals(expected, result);
	}
	
	@Test
	public void testNarrowerOriginProportional(){
		marginDimension = new Dimension(400, 200);
		originalDimension = new Dimension(500, 1500);
		
		expected = new Dimension(66, 200);
		result = imageResizer.getTargetDimensionByMargins(marginDimension , originalDimension);		
		log.info("margin: "+marginDimension+", originals: "+originalDimension+", result is: " + result);
		assertEquals(expected, result);
	}
	
	@Test
	public void testHigherOriginProportional(){
		marginDimension = new Dimension(200, 400);
		originalDimension = new Dimension(1500, 500);
		expected = new Dimension(200, 66);
		result = imageResizer.getTargetDimensionByMargins(marginDimension , originalDimension);		
		log.info("margin: "+marginDimension+", originals: "+originalDimension+", result is: " + result);
		assertEquals(expected, result);
	}
	
	@Test
	public void testSmallerVOriginProportional(){
		marginDimension = new Dimension(400, 400);
		originalDimension = new Dimension(100, 200);
						
		imageResizer.setScaleToMax(false);
		expected = new Dimension(100, 200);
		result = imageResizer.getTargetDimensionByMargins(marginDimension , originalDimension);		
		log.info("margin: "+marginDimension+", originals: "+originalDimension+", result is: " + result);
		assertEquals(expected, result);
		
		imageResizer.setScaleToMax(true);
		expected = new Dimension(200, 400);
		result = imageResizer.getTargetDimensionByMargins(marginDimension , originalDimension);		
		log.info("margin: "+marginDimension+", originals: "+originalDimension+", result is: " + result);
		assertEquals(expected, result);
	}
	
	@Test
	public void testSmallerHOriginProportional(){
		marginDimension = new Dimension(400, 400);
		originalDimension = new Dimension(200, 100);
		
		imageResizer.setScaleToMax(false);
		expected = new Dimension(200, 100);
		result = imageResizer.getTargetDimensionByMargins(marginDimension , originalDimension);		
		log.info("margin: "+marginDimension+", originals: "+originalDimension+", result is: " + result);
		assertEquals(expected, result);
		
		
		imageResizer.setScaleToMax(true);
		expected = new Dimension(400, 200);
		result = imageResizer.getTargetDimensionByMargins(marginDimension , originalDimension);		
		log.info("margin: "+marginDimension+", originals: "+originalDimension+", result is: " + result);
		assertEquals(expected, result);
	}

}
