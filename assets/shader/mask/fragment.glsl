#ifdef GL_ES
precision highp float;
#endif

varying vec4 v_color;
varying vec2 v_texCoord;

uniform sampler2D u_tex0; //default GL_TEXTURE0, expected by SpriteBatch
uniform sampler2D u_tex1;
uniform float     u_time;

void main(void) {
	vec2 st = v_texCoord.xy;
	vec4 texColor0 = texture2D(u_tex0, v_texCoord);

	st.x += 1.;
	st.x -= u_time * 4.;

	vec4 texColor1 = texture2D(u_tex1, st);
	vec3 c = mix(texColor0.rgb, texColor1.rgb, texColor1.a);

  gl_FragColor = vec4(c, texColor0.a);
}