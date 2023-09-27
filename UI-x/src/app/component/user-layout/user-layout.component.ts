import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { UserService } from 'src/app/service/user.service';
import { saveAs } from 'file-saver';
import { FileNamePipe } from 'src/app/pipe/file-name.pipe';
import { FileTypePipe } from 'src/app/pipe/file-type.pipe';

@Component({
  selector: 'app-user-layout',
  templateUrl: './user-layout.component.html',
  styleUrls: ['./user-layout.component.css']
})
export class UserLayoutComponent implements OnInit
{
  file: File = null;
  filePromt: string = "Drag & Drop files here or click to browse";

  FileList = new Array<any>();

  constructor(private usr: UserService, private tostr: ToastrService, private fn: FileNamePipe, private ft: FileTypePipe){}

  ngOnInit(): void
  {
    this.getFileList();
  }

  private getFileList(): void
  {
    this.usr.getFiles().subscribe({
      next: (res)=>
        {
          this.FileList.length = 0;

          console.log(res);
          const data = res.data;

          data.fileList.forEach(element =>
            {
              this.FileList.push(element);
            });
          // this.tostr.success("File uploaded successfully");
        },
        error: (err)=>
        {
          console.error(err);
          // this.tostr.error("File upload error");
        }});
  }

  public onFilesDropped(files: File): void
  {
    this.file = files[0];
    console.log('Dropped files:', this.file);
    // console.log(file==null);

    // if(this.file)
    // {
    //   this.filePromt = this.file.name;
    // }
  }

  public upload(): void
  {
    if(this.file)
    {
      this.usr.uploadFile(this.file).subscribe({
        next: (res)=>
        {
          console.log(res);
          this.getFileList();
          this.tostr.success("File uploaded successfully");
          this.file = null;
        },
        error: (err)=>
        {
          console.error(err);
          this.tostr.error("File upload error");
        }});
    }
    else
    {
      this.tostr.warning("No file selected");
    }
  }

  public deleteFile(file: any): void
  {
    const id = file.id;
    this.usr.deleteFile(id).subscribe({
      next: (res)=>
      {
        this.getFileList();
        this.tostr.success("File deleted successfully");
      },
      error: (err)=>
      {
        this.tostr.error("File deletion error");
        console.error(err);
      }
    });
  }

  public downloadFile(file: any)
  {
    const id = file.id;
    this.usr.downloadFile(id).subscribe({
      next: (res)=>
      {
        console.log(res);
        saveAs(res,this.fn.transform(file.fileName));
      },
      error: (err)=>
      {
        this.tostr.error("File deletion error");
        console.error(err);
      }
    });
  }
}
