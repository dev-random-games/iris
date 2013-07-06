package co.devrandom.vc.view;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class Shader {
	public int program;

	private boolean canShade;

	public Shader(String vertPath, String fragPath) {
		int vertShader = 0;
		int fragShader = 0;
		canShade = false;

		try {
			vertShader = createShader(vertPath,
					ARBVertexShader.GL_VERTEX_SHADER_ARB);
			fragShader = createShader(fragPath,
					ARBFragmentShader.GL_FRAGMENT_SHADER_ARB);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		} finally {
			if (vertShader == 0 || fragShader == 0)
				return;
		}

		program = ARBShaderObjects.glCreateProgramObjectARB();

		if (program == 0)
			return;

		ARBShaderObjects.glAttachObjectARB(program, vertShader);
		ARBShaderObjects.glAttachObjectARB(program, fragShader);

		ARBShaderObjects.glLinkProgramARB(program);
		ARBShaderObjects.glLinkProgramARB(program);
		if (ARBShaderObjects.glGetObjectParameteriARB(program,
				ARBShaderObjects.GL_OBJECT_LINK_STATUS_ARB) == GL11.GL_FALSE) {
			System.err.println(getLogInfo(program));
			return;
		}

		ARBShaderObjects.glValidateProgramARB(program);
		if (ARBShaderObjects.glGetObjectParameteriARB(program,
				ARBShaderObjects.GL_OBJECT_VALIDATE_STATUS_ARB) == GL11.GL_FALSE) {
			System.err.println(getLogInfo(program));
			return;
		}

		canShade = true;
	}

	private int createShader(String filename, int shaderType) throws Exception {
		int shader = 0;
		try {
			shader = ARBShaderObjects.glCreateShaderObjectARB(shaderType);

			if (shader == 0)
				return 0;

			ARBShaderObjects.glShaderSourceARB(shader,
					readFileAsString(filename));
			ARBShaderObjects.glCompileShaderARB(shader);

			if (ARBShaderObjects.glGetObjectParameteriARB(shader,
					ARBShaderObjects.GL_OBJECT_COMPILE_STATUS_ARB) == GL11.GL_FALSE)
				throw new RuntimeException("Error creating shader: "
						+ getLogInfo(shader));

			return shader;
		} catch (Exception exc) {
			ARBShaderObjects.glDeleteObjectARB(shader);
			throw exc;
		}
	}

	private static String getLogInfo(int obj) {
		return ARBShaderObjects.glGetInfoLogARB(obj, ARBShaderObjects
				.glGetObjectParameteriARB(obj,
						ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB));
	}

	private String readFileAsString(String filename) throws Exception {
		StringBuilder source = new StringBuilder();

		FileInputStream in = new FileInputStream(filename);

		Exception exception = null;

		BufferedReader reader;
		try {
			reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

			Exception innerExc = null;
			try {
				String line;
				while ((line = reader.readLine()) != null)
					source.append(line).append('\n');
			} catch (Exception exc) {
				exception = exc;
			} finally {
				try {
					reader.close();
				} catch (Exception exc) {
					if (innerExc == null)
						innerExc = exc;
					else
						exc.printStackTrace();
				}
			}

			if (innerExc != null)
				throw innerExc;
		} catch (Exception exc) {
			exception = exc;
		} finally {
			try {
				in.close();
			} catch (Exception exc) {
				if (exception == null)
					exception = exc;
				else
					exc.printStackTrace();
			}

			if (exception != null)
				throw exception;
		}

		return source.toString();
	}

	public boolean canShade() {
		return canShade;
	}

	public boolean useShader() {
		if (canShade)
			ARBShaderObjects.glUseProgramObjectARB(program);
		return canShade;
	}

	public void haltShader() {
		if (canShade)
			ARBShaderObjects.glUseProgramObjectARB(0);
	}

	public void deleteShader() {
		ARBShaderObjects.glDeleteObjectARB(program);
		canShade = false;
	}

	private int getUniform(String name) {
		return GL20.glGetUniformLocation(program, name);
	}

	public void setUniform1f(String name, float v1) {
		int uniform = getUniform(name);
		GL20.glUniform1f(uniform, v1);
	}

	public void setUniform2f(String name, float v1, float v2) {
		int uniform = getUniform(name);
		GL20.glUniform2f(uniform, v1, v2);
	}

	public void setUniform3f(String name, float v1, float v2, float v3) {
		int uniform = getUniform(name);
		GL20.glUniform3f(uniform, v1, v2, v3);
	}

	public void setUniform4f(String name, float v1, float v2, float v3, float v4) {
		int uniform = getUniform(name);
		GL20.glUniform4f(uniform, v1, v2, v3, v4);
	}
	
	public void setUniform1i(String name, int v1) {
		int uniform = getUniform(name);
		GL20.glUniform1i(uniform, v1);
	}

	public void setUniform2i(String name, int v1, int v2) {
		int uniform = getUniform(name);
		GL20.glUniform2i(uniform, v1, v2);
	}

	public void setUniform3i(String name, int v1, int v2, int v3) {
		int uniform = getUniform(name);
		GL20.glUniform3i(uniform, v1, v2, v3);
	}

	public void setUniform4i(String name, int v1, int v2, int v3, int v4) {
		int uniform = getUniform(name);
		GL20.glUniform4i(uniform, v1, v2, v3, v4);
	}
}
