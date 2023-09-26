import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'fileName',
  pure: true
})
export class FileNamePipe implements PipeTransform
{

  transform(filePath: string): string
   {
    if (!filePath)
    {
      return '';
    }

    // Find the last occurrence of the backslash character
    const lastIndex = filePath.lastIndexOf('\\');
    // Extract the filename from the path
    const filename = filePath.substring(lastIndex + 1);

    return filename;
  }

}
