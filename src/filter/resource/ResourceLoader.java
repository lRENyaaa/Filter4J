package filter.resource;

import java.io.IOException;
import java.io.InputStream;

public interface ResourceLoader {

    InputStream getResource(String path) throws IOException;

}
