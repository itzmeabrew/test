import { Directive, EventEmitter, HostListener, Output } from '@angular/core';

@Directive({
  selector: '[appFileDragDrop]'
})
export class FileDragDropDirective
{
  @Output() filesDropped = new EventEmitter<File>();

  @HostListener('dragover', ['$event']) onDragOver(event: any)
  {
    event.preventDefault();
    event.stopPropagation();
  }

  @HostListener('dragleave', ['$event']) onDragLeave(event: any)
  {
    event.preventDefault();
    event.stopPropagation();
  }

  @HostListener('drop', ['$event']) onDrop(event: any)
  {
    event.preventDefault();
    event.stopPropagation();
    const files = event.dataTransfer.files;
    if (files.length > 0)
    {
      this.filesDropped.emit(files);
    }
  }

  @HostListener('click', ['$event']) onClick(event: any)
  {
    const inputElement = document.createElement('input');
    inputElement.type = 'file';
    inputElement.style.display = 'none';

    inputElement.addEventListener('change', (e: any) =>
    {
      const files = e.target.files;
      if (files.length > 0) {
        this.filesDropped.emit(files);
      }
    });

    inputElement.click();
  }
}
