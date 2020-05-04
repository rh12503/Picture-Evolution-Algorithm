package pea.util;

import pea.ga.Image;
import pea.pixelbuffer.PixelBufferByte;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class ImageUtil {
	public static Image createImage(BufferedImage image) {
		if (image.getType() != BufferedImage.TYPE_3BYTE_BGR) {
			image = ImageUtil.convert(image, BufferedImage.TYPE_3BYTE_BGR);
		}

		byte[] imagePixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		PixelBufferByte buffer = new PixelBufferByte();
		buffer.setPixels(imagePixels);
		return new Image(image.getWidth(), image.getHeight(), buffer);
	}

	public static BufferedImage convert(BufferedImage source, int model) {
		BufferedImage image = new BufferedImage(source.getWidth(), source.getHeight(), model);
		Graphics2D g = image.createGraphics();
		g.drawImage(source, 0, 0, null);
		g.dispose();
		return image;
	}
}
