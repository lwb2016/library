package east2d.com.myapplication.gl;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GLSurfaceView glSurfaceView = new GLSurfaceView(this);
        glSurfaceView.setEGLContextClientVersion(2); // Pick an OpenGL ES 2.0 context.
        glSurfaceView.setRenderer(new MyRender());
        setContentView(glSurfaceView);
    }
}
