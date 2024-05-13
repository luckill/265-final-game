package tage;
import static com.jogamp.opengl.GL4.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.gl2.GLUT;
import org.joml.*;

/**
* Manages up to two HUD strings, implemented as GLUT strings.
* This class is instantiated automatically by the engine.
* Note that this class utilizes deprectated OpenGL functionality.
* <p>
* The available fonts are:
* <ul>
* <li> GLUT.BITMAP_8_BY_13
* <li> GLUT.BITMAP_9_BY_15
* <li> GLUT.BITMAP_TIMES_ROMAN_10
* <li> GLUT.BITMAP_TIMES_ROMAN_24
* <li> GLUT.BITMAP_HELVETICA_10
* <li> GLUT.BITMAP_HELVETICA_12
* <li> GLUT.BITMAP_HELVETICA_18
* </ul>
* <p>
* This class includes a "kludge".  On many systems, GLUT strings ignore the glColor
* setting and uses the most recent color rendered.  Therefore, this HUD
* renderer first renders a single pixel at the desired HUD color at a
* distant location, before drawing the HUD.
* @author Scott Gordon
*/

public class HUDmanager
{	private GLCanvas myCanvas;
	private GLUT glut = new GLUT();
	private Engine engine;

	private String HUD1string, HUD2string, HUD3string, HUD4string;
	private float[] HUD1color, HUD2color, HUD3color, HUD4color;
	private int HUD1font = GLUT.BITMAP_TIMES_ROMAN_24;
	private int HUD2font = GLUT.BITMAP_TIMES_ROMAN_24;
	private int HUD3font = GLUT.BITMAP_TIMES_ROMAN_24;
	private int HUD4font = GLUT.BITMAP_TIMES_ROMAN_24;
	private int HUD1x, HUD1y, HUD2x, HUD2y, HUD3x, HUD3y, HUD4x, HUD4y;
	private int hudColorProgram;

	// The constructor is called by the engine, and should not be called by the game application.
	// It initializes the two HUDs to empty strings.

	protected HUDmanager(Engine e)
	{	engine = e;
		HUD1string = "";
		HUD2string = "";
		HUD3string = "";
		HUD4string = "";
		HUD1color = new float[3];
		HUD2color = new float[3];
		HUD3color = new float[3];
		HUD4color = new float[3];
	}
	
	protected void setGLcanvas(GLCanvas g) { myCanvas = g; }

	protected void drawHUDs(int hcp)
	{	//GL4 gl = (GL4) GLContext.getCurrentGL();
		GL4 gl4 = myCanvas.getGL().getGL4();
		GL4bc gl4bc = (GL4bc) gl4;

		gl4bc.glWindowPos2d (HUD1x, HUD1y);
		prepHUDcolor(HUD1color, hcp);
		glut.glutBitmapString(HUD1font, HUD1string);

		gl4bc.glWindowPos2d (HUD2x, HUD2y);
		prepHUDcolor(HUD2color, hcp);
		glut.glutBitmapString (HUD2font, HUD2string);

		gl4bc.glWindowPos2d (HUD3x, HUD3y);
		prepHUDcolor(HUD3color, hcp);
		glut.glutBitmapString (HUD3font, HUD3string);

		gl4bc.glWindowPos2d (HUD4x, HUD4y);
		prepHUDcolor(HUD4color, hcp);
		glut.glutBitmapString (HUD4font, HUD4string);
	}

	/** sets HUD #1 to the specified text string, color, and location */
	public void setHUD1(String string, Vector3f color, int x, int y)
	{	HUD1string = string;
		HUD1color[0]=color.x(); HUD1color[1]=color.y(); HUD1color[2]=color.z();
		HUD1x = x;
		HUD1y = y;
	}

	/** sets HUD #2 to the specified text string, color, and location */
	public void setHUD2(String string, Vector3f color, int x, int y)
	{	HUD2string = string;
		HUD2color[0]=color.x(); HUD2color[1]=color.y(); HUD2color[2]=color.z();
		HUD2x = x;
		HUD2y = y;
	}

	/** sets HUD #3 to the specified text string, color, and location */
	public void setHUD3(String string, Vector3f color, int x, int y)
	{	HUD3string = string;
		HUD3color[0]=color.x(); HUD3color[1]=color.y(); HUD3color[2]=color.z();
		HUD3x = x;
		HUD3y = y;
	}

	/** sets HUD #4 to the specified text string, color, and location */
	public void setHUD4(String string, Vector3f color, int x, int y)
	{	HUD4string = string;
		HUD4color[0]=color.x(); HUD4color[1]=color.y(); HUD4color[2]=color.z();
		HUD4x = x;
		HUD4y = y;
	}


	/** sets HUD #1 font - available fonts are listed above. */
	public void setHUD1font(int font) { HUD1font = font; }

	/** sets HUD #2 font - available fonts are listed above. */
	public void setHUD2font(int font) { HUD2font = font; }

	/** sets HUD #3 font - available fonts are listed above. */
	public void setHUD3font(int font) { HUD3font = font; }

	/** sets HUD #3 font - available fonts are listed above. */
	public void setHUD4font(int font) { HUD4font = font; }

	// Kludge to ensure HUD renders with correct color - do not call from game application.
	// Draws a single dot at a distant location to set the desired HUD color.
	// Used internally by the renderer.

	private void prepHUDcolor(float[] color, int hcp)
	{	GL4 gl = (GL4) GLContext.getCurrentGL();
		gl.glUseProgram(hcp);
		int hudCLoc = gl.glGetUniformLocation(hcp, "hudc");
		gl.glProgramUniform3fv(hcp, hudCLoc, 1, color, 0);
		gl.glDrawArrays(GL_POINTS,0,1);
	}
}