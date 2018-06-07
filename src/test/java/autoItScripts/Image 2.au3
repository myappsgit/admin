#cs ----------------------------------------------------------------------------

 AutoIt Version: 3.3.14.2
 Author:         Darshan

 Script Function:
	Upload file AutoIt script.

#ce ----------------------------------------------------------------------------

; Script Start - Add your code below here
WinWaitActive("File Upload")

; Set input focus to the edit control of Upload window using the handle returned by WinWaitActive
ControlFocus ( "File Upload", "", "Edit1")

; Set the File name text on the Edit field

ControlSetText("File Upload", "", "Edit1", "image2.jpg")

; Click on the Open button

ControlClick("File Upload", "","Button1");

;Send("C:\Images\image2.jpg")

;Send("{ENTER}")


