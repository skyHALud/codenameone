#import "DrawImage.h"
#import "CodenameOne_GLViewController.h"
#include "xmlvm.h"

@implementation DrawImage
-(id)initWithArgs:(int)a xpos:(int)xpos ypos:(int)ypos i:(GLUIImage*)i w:(int)w h:(int)h {
    alpha = a;
    x = xpos;
    y = ypos;
    width = w;
    height = h;
    img = i;
#ifndef CN1_USE_ARC
    [img retain];
#endif
    return self;
}

-(void)execute {
    _glColor4f(((float)alpha) / 255.0f, ((float)alpha) / 255.0f, ((float)alpha) / 255.0f, ((float)alpha) / 255.0f);
    glActiveTexture(GL_TEXTURE0);
    _glEnable(GL_TEXTURE_2D);
    GLErrorLog;
    int w = width;
    int h = height;
    float actualImageWidth = [img getImage].size.width;
    float actualImageHeight = [img getImage].size.height;
    int actualImageWidthP2 = nextPowerOf2((int)actualImageWidth);
    int actualImageHeightP2 = nextPowerOf2((int)actualImageHeight);
    GLuint tex = [img getTexture:(int)actualImageWidth texHeight:(int)actualImageHeight];
    glBindTexture(GL_TEXTURE_2D, tex);
    GLErrorLog;
    float blankPixelsW = actualImageWidthP2 - actualImageWidth;
    float blankPixelsH = actualImageHeightP2 - actualImageHeight;
    w += blankPixelsW * (w / actualImageWidth);//nextPowerOf2(w);//actualImageWidthP2 - actualImageWidth;
    h += blankPixelsH * (h / actualImageHeight); //nextPowerOf2(h);//actualImageHeightP2 - actualImageHeight;
    GLfloat vertexes[] = {
        x, y,
        x + w, y,
        x, y + h,
        x + w, y + h
    };
    //NSLog(@"drawImage(%i, %i, %i, %i, %i, %i)", x, y, w, h, width, height);
    
    static const GLshort textureCoordinates[] = {
        0, 1,
        1, 1,
        0, 0,
        1, 0,
    };
    
    //glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
    //glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    GLErrorLog;
    _glEnableClientState(GL_VERTEX_ARRAY);
    GLErrorLog;
    _glEnableClientState(GL_TEXTURE_COORD_ARRAY);
    GLErrorLog;
    _glTexCoordPointer(2, GL_SHORT, 0, textureCoordinates);
    GLErrorLog;
    _glVertexPointer(2, GL_FLOAT, 0, vertexes);
    GLErrorLog;
    _glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);
    GLErrorLog;
    _glDisableClientState(GL_VERTEX_ARRAY);
    GLErrorLog;
    _glDisableClientState(GL_TEXTURE_COORD_ARRAY);
    GLErrorLog;
    glBindTexture(GL_TEXTURE_2D, 0);
    GLErrorLog;
    _glDisable(GL_TEXTURE_2D);
    GLErrorLog;
}

-(void)dealloc {
#ifndef CN1_USE_ARC
    [img release];
    [super dealloc];
#endif
}

-(NSString*)getName {
    return @"DrawImage";
}

@end
