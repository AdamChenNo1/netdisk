package Config;

import Filter.AuthRequestFilter;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

//@ApplicationPath("webapi")
public class RestApplication extends ResourceConfig {
    public RestApplication(){
        this.packages("/");
        this.register(MultiPartFeature.class);
        this.register(AuthRequestFilter.class);
        //this.register(CorsResponseFilter.class);
        //this.register(new FastJsonBodyReader());
        //this.register(new FastJsonBodyWriter());
    }
}
