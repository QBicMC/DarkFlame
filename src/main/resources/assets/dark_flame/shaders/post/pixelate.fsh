#version 150

uniform sampler2D InSampler;
uniform vec2 InSize;

in vec2 texCoord;

uniform float PixelIntensity; // Controls pixelation intensity

out vec4 fragColor;

void main(){
    float pixelSize = 1.0 + PixelIntensity * 32.0;
    vec2 pixelScale = pixelSize / InSize;
    vec2 pixelatedCoord = floor(texCoord / pixelScale) * pixelScale;
    vec4 pixelatedColor = texture(InSampler, pixelatedCoord);
    fragColor = vec4(pixelatedColor.rgb, 1.0);
}