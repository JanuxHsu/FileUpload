<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<formname="CelerFTFileUpload"> <fieldsetclass="top">
	<legendid="legend_uploadfileform">CelerFT File Uploader</legend>
	<ol style="list-style: none">
		<liclass="form"> <labelfor
			="select_directory" style="float:left;width:410px"> Please
		enter the name for the destination directory: </label> <inputtype
			="text" id="select_directory" name="directoryname"
			style="width:200px" />
		<li />
		<li class="form"><labelfor
				="select_file" style="float:left;width:410px"> Please
			select up to 5 files to upload: </label> <inputtype ="file" id="select_file"
				name="files[]" multiple style="width:200px" /></li>
		<liclass="form"> <labelfor
			="select_asyncstate" style="float:left;width:410px">
		Upload files greater than 500MB in size using asynchronous mode: <fontcolor="blue">(Note
		asynchronous uploads is used for smaller files.)</font> </label> <inputtype
			="checkbox" id="select_asyncstate" name="asyncstate" />
		</li>
		<liclass="form">
		<p></p>
		</li>
		<liclass="form"> <labelfor
			="select_bytesperchunk" style="float:left;width:410px">
		Select the bytes per chunk: </label> <selectid
			="select_bytesperchunk" name="bytesperchunk" style="width:200px">
		<optionvalue="1">50MB
		</option>
		<optionvalue="2">20MB
		</option>
		<optionvalue="3">10MB
		</option>
		<optionvalue="4">5MB
		</option>
		<optionvalue="5">2MB
		</option>
		<optionselected ="selected" value="6">1MB
		</option>
		<optionvalue="7">500K
		</option>
		<optionvalue="8">256K
		</option>
		<optionvalue="9">128K
		</option>
		<optionvalue="10">64K
		</option>
		</select>
		</li>
		<liclass="form"> <inputtype ="button" id="cancel_workers"
			value="Cancel Uploads" style="float:right;margin-left: 10px" /> <inputtype
			="submit" id="upload_file" value="Upload File" style="float:right" />
		</li>
	</ol>
	</fieldset>
	</form>
</body>
</html>