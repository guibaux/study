#include <stdio.h>


int
main(void) {
    int vla_length;
    puts("Gimme the array length");
    scanf("%d",&vla_length); // Bad could overflow
    
    int vla[vla_length]; // The stack may cry
    
    int i = 0;

    do{
	printf("%d\n", vla[i]);
	vla[i] = 0;
        i++;
    }while(i<vla_length);
    puts("OK");

    return 0;
}
