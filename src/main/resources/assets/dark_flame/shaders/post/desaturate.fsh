#version 150

uniform sampler2D InSampler;

in vec2 texCoord;

uniform float InverseAmount;

out vec4 fragColor;

void main(){
    vec4 diffuseColor = texture(InSampler, texCoord);

    float gray = dot(diffuseColor.rgb, vec3(0.299, 0.587, 0.114));
    vec4 grayColor = vec4(gray, gray, gray, diffuseColor.a);

    vec4 outColor = mix(diffuseColor, grayColor, InverseAmount);
    fragColor = vec4(outColor.rgb, 1.0);
}
