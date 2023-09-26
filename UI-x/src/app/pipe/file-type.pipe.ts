import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'fileType',
  pure: true
})
export class FileTypePipe implements PipeTransform
{

  transform(filePath: string): string
  {
    if (!filePath) {
      return '';
    }

    // Find the last occurrence of the dot character to identify the extension
    const lastIndex = filePath.lastIndexOf('.');
    // Extract the file type (extension) from the path
    const fileType = filePath.substring(lastIndex + 1);

    return fileType;
  }

}
