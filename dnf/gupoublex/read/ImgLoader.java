package dnf.gupoublex.read;

//import java.text.SimpleDateFormat;
//import dnf.gupoublex.set.SetBase;
//import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

public class ImgLoader extends AsynchronousAssetLoader<Img, ImgLoader.ImgParameter> {
	private Img img = null;
	public ImgLoader(FileHandleResolver resolver) {
		super(resolver);
	}
	@SuppressWarnings("rawtypes")
	@Override
	public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, ImgParameter parameter) {
		return null;
	}
	static public class ImgParameter extends AssetLoaderParameters<Img> {
	}
	@Override
	public void loadAsync(AssetManager manager, String fileName, FileHandle file, ImgParameter parameter) {
		img = new Img(file);
	}
	@Override
	public Img loadSync(AssetManager manager, String fileName, FileHandle file, ImgParameter parameter) {
		Img img = this.img;
	//	long start = System.currentTimeMillis();
		img.read();
	//	Gdx.app.log(SetBase.TAG, "loadtime = "+new SimpleDateFormat("mm:ss.SSS").format(System.currentTimeMillis()-start)+"S -> "+file);
		return img;
	}
}