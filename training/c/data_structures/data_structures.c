#include <stdio.h>
#include <stdlib.h>

typedef struct linked_list {
  int data;
  struct linked_list *next;
} linkedl;


linkedl* lklinit(int data) {
	linkedl *new_list = malloc(sizeof(linkedl));
	new_list->next = NULL;
	new_list->data = data;
	return new_list;
}


void lkladd(linkedl *list, int data) {
    list->next = lklinit(data);
}

linkedl* lklget(linkedl *list, int pos) {
  if(pos < 0) return NULL;
  if(pos == 0) return list;

  linkedl *temp = lklinit(0);
    for(int i = 0;i<=pos;i++){
      if(temp->next == NULL) break;
      temp = temp->next;
    }
    return temp;
}

int
main(void) {
	linkedl* list = lklinit(10);

	printf("Head %d\n", list->data);
	// for(int i = 0;i<9;i++)
  lkladd(list, 14);
  linkedl* box = lklget(list, -1);
  printf("Tail %d\n", list->next->data);

  if(box == NULL){
    free(box);
    puts("Failed to get");
    exit(1);
  }

  printf("Box %d\n", box->data);
}
