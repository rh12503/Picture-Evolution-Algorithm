package pea.io;

import pea.ga.World;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class WorldIO {

	public static World read(InputStream is) throws IOException, ClassNotFoundException {
		GZIPInputStream gis = new GZIPInputStream(is);
		ObjectInputStream ois = new ObjectInputStream(gis);
		Object o = ois.readObject();
		ois.close();
		return (World) o;
	}

	public static World read(Path path) throws IOException, ClassNotFoundException {
		return read(Files.newInputStream(path));
	}

	public static OutputStream write(OutputStream is, World world) throws IOException, ClassNotFoundException {
		GZIPOutputStream gis = new GZIPOutputStream(is);
		ObjectOutputStream ois = new ObjectOutputStream(gis);
		ois.writeObject(world);
		ois.close();
		return is;
	}

	public static Path write(Path path, World world) throws IOException, ClassNotFoundException {
		write(Files.newOutputStream(path), world);
		return path;
	}
}
