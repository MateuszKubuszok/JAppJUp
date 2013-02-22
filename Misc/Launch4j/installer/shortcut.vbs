set objWSHShell = CreateObject("WScript.Shell")
sShortcut = objWSHShell.ExpandEnvironmentStrings(WScript.Arguments.Item(0))
sTargetPath = objWSHShell.ExpandEnvironmentStrings(WScript.Arguments.Item(1))
sIconPath = objWSHShell.ExpandEnvironmentStrings(WScript.Arguments.Item(2))

set objSC = objWSHShell.CreateShortcut(sShortcut) 
With objSC
	.TargetPath = sTargetPath
	.IconLocation = sIconPath
	.Save
End With