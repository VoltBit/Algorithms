#include <iostream>
#include <math.h>
#include "GL/freeglut.h"
#include "GL/gl.h"
const float DEG2RAD = 3.1415 / 180;
void drawTriangle(){
	glClearColor(0.4, 0.4, 0.4, 0.4);
	glClear(GL_COLOR_BUFFER_BIT);

	glColor3f(1.0, 1.0, 1.0);
	glOrtho(-1.0, 1.0, -1.0, 1.0, -1.0, 1.0);

	glBegin(GL_TRIANGLES);
	glVertex3f(-0.7, 0.7, 0);
	glVertex3f(0.7, 0.7, 0);
	glVertex3f(0, -1, 0);
	glEnd();

	glFlush();
}

void drawCircle(){
	float cx = 100, cy = 100, r = 20;
	int num_segments = 100;
	glBegin(GL_LINE_LOOP);
	int i;
	for(i = 0; i < num_segments; i++){
		float angle = 2.0f * 3.14159 * float(i) / float(num_segments);
		float x = r * cosf(angle);
		float y = r * sinf(angle);
		glVertex2f(x + cx, y + cy);
	}
	glEnd();
	glFlush();
}

// void circle(){
// 	drawCircle(100,100,20,100);
// }

int main(int argc, char **argv){
	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_SINGLE);
	glutInitWindowSize(500, 500);
	glutInitWindowPosition(100, 100);
	glutCreateWindow("OpenGL test");
	// glutDisplayFunc(drawTriangle);
	glutDisplayFunc(drawCircle);
	
	glutMainLoop();
	return 0;
}