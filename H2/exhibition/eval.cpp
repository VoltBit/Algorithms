#include <stdio.h>
#include <string.h>

/*
	Tema 2 PA 2015 Problema 2 OutputChecker
	Autor: Manica Vlad Bogdan

	Descriere program:

	In functie de testul oficial de intrare si un output, se verifica corectitudinea solutiei.
	Se printeaza la stderr un mesaj in cazul in care solutia este gresita.
	In plus, in cazul in care numarul de arce difera de numarul muchiilor, se printeaza la
	stderr mesajul predefinit ca err1.
	Pentru cazul in care testul trece se va printa "OK!".
*/

#define err1 "Numar incorect de arce!\n"
#define err2 "Incorect! :( \n"
#define err3 "Eroare la citire fisier intrare!\n"

#define dim 5005

int muchii[dim];
int arce[dim];
int n, x, y, m = 0, ok;
char str[50];

void corect()
{
	printf("OK!\n");
}
	
int main()
{
	FILE *f = fopen("expozitie.in","r"), *ff;

	fscanf(f,"%d",&n);
	
	// Testare test-9 Imposibil.
	if (n == 10) {
		ff = fopen("expozitie.out","r");
		fscanf(ff,"%s\n",str);
		if (strcmp(str,"Imposibil") == 0) {
			corect();
			fclose(ff);
			return 0;
		} else {
			fprintf(stderr, err2);
			fclose(ff);
			return 0;
		}
	}

	while (fscanf(f,"%d %d",&x,&y) >= 0) {
		muchii[x]++;
		muchii[y]++;
		m ++;
	}
	if (m == 0) {
		fprintf(stderr, err3);
		fclose(f);
		return 0;
	}
	fclose(f);
	
	f = fopen("expozitie.out","r");
	ok = 1;
	while (fscanf(f,"%d %d",&x,&y) > 0) {
		m--;
		if (m < 0) {
			break;
		}
		muchii[x]--;
		muchii[y]--;
		arce[x]++;		
	}
	fclose(f);
	
	if (m != 0) {
		fprintf(stderr, err1);
		return 0;
	}
	
	for (int i = 0; i < dim; i++) {
		if (muchii[i] != 0 || arce[i] % 2 != 0) {
			fprintf(stderr, err2);
			return 0;
		}
	}
	corect();
	return 0;
}
