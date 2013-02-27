import argparse, httplib, mimetypes, sys
from io import BytesIO
from os.path import basename

'''
Defines default values.
Should provide values that would be used most frequently/always.
Used values should be considered safe.
''' 
default_type = 'UNZIP'
default_relative_path = '/'
default_updater_command = ''

'''
Parses arguments.
Use upload.py --help to learn full documentation about scripts usage.
'''
parser = argparse.ArgumentParser(description='Post new update on server.')
parser.add_argument('--username', dest='username', nargs=1, required=True, help='uploader\'s username')
parser.add_argument('--password', dest='password', nargs=1, required=True, help='uploader\'s password')
parser.add_argument('--program', dest='program', nargs=1, required=True, help='program\s name')
parser.add_argument('--package', dest='package', nargs=1, required=True, help='installation\s name')
parser.add_argument('--version', dest='version', nargs=1, required=True, help='version number of uploaded update')
parser.add_argument('--devver', dest='development_version', action='store_true', default=False, help='set development version')
parser.add_argument('--relver', dest='development_version', action='store_false', default=False, help='set development version')
parser.add_argument('--type', dest='type', nargs='?', default=default_type, help='type of update (COPY/UNZIP/EXECUTE)')
parser.add_argument('--changelog', dest='changelog', nargs=1, required=True, help='changes done in this update')
parser.add_argument('--file', dest='file', nargs=1, required=True, help='absolute path to uploaded file')
parser.add_argument('--relpath', dest='relative_path', nargs='?', default=default_relative_path, help='relative path for update')
parser.add_argument('--command', dest='updater_command', nargs='?', default=default_updater_command, help='updater\'s command')
args = parser.parse_args()

'''
Declares values used in form.
Values are retrived from argument parser to ensure that default optional values
would be used where no value passed, as well as ensure that all required values
were passed.
'''
username = args.username[0]
password = args.password[0]
program = args.program[0]
package = args.package[0]
version = args.version[0]
development_version = args.development_version[0]
changelog = args.changelog[0]
type = args.type
filename = basename(args.file[0])
file = open(args.file[0], 'rb')
relative_path = args.relative_path
updater_command = args.updater_command

'''
Defines hardcoded values used during connection.
They are used to connect to server and upload update.
Assuming that connects through proxy:
	host = '10.159.32.155'
	port = 8080
	selector = "http://%s:%i%s" % (repo_host,repo_port,repo_uri)
otherwise:
	host = repo_host
	port = repo_port
	selector = repo_uri
'''
repo_host = '10.159.69.221'
repo_port = 8080
repo_uri = '/ran_dim_repo/api/upload_file?displayError=true'

#host = '10.159.32.155'
#port = 8080
#selector = "http://%s:%i%s" % (repo_host,repo_port,repo_uri)
host = repo_host
port = repo_port
selector = repo_uri

'''
Creates connection and posts request.
'''
def send_post_multipart():
	print("preparing request body")
	body, content_type = prepare_body()
	print("preparing headers")
	headers = {
		'User-Agent': 'python_multipart_caller',
		'Content-Type': content_type
	}
	# Performs actual connection.
	print("performing request")
	connection = httplib.HTTPConnection(host, port)
	connection.request('POST', selector, body, headers)
	# Checks the result of upload.
	print("preparing results")
	result = connection.getresponse()
	print(result.status)
	return result.status == httplib.OK

'''
Creates request body.
'''
def prepare_body():
	boundary = '----------AaB03x'
	form = []
	# Prepares fields.
	set_form_field(form, 'username', username, boundary)
	set_form_field(form, 'password', password, boundary)
	set_form_field(form, 'program', program, boundary)
	set_form_field(form, 'thePackage', package, boundary)
	set_form_field(form, 'version', version, boundary)
	set_form_field(form, 'developmentVersion', development_version, boundary)
	set_form_field(form, 'changelog', changelog, boundary)
	set_form_field(form, 'type', type, boundary)
	set_form_field(form, 'relativePath', relative_path, boundary)
	set_form_field(form, 'updaterCommand', updater_command, boundary)
	# Prepares file.
	set_form_file(form, 'file', filename, file, boundary)
	# Calculates body
	form.append('--' + boundary + '--')
	form.append('')
	s = BytesIO()
	for element in form:
		s.write(str(element))
		s.write('\r\n')
	body = s.getvalue()
	content_type = 'multipart/form-data; boundary=%s' % boundary
	return body, content_type

'''
Adds field's value to form.
'''
def set_form_field(form, name, value, boundary):
	form.append('--' + boundary)
	form.append('Content-Disposition: form-data; name="%s"' % name)
	form.append('')
	form.append(value)

'''
Adds file's value to form.
'''
def set_form_file(form, name, filename, file, boundary):
	form.append('--' + boundary)
	form.append('Content-Disposition: form-data; name="%s"; filename="%s"' % (name, filename))
	form.append('Content-Type: %s' % get_content_type(filename))
	form.append('')
	form.append(file.read())

'''
Tries to guess content from extension.
'''
def get_content_type(filename):
	return mimetypes.guess_type(filename)[0] or 'application/octet-stream'

'''
Tries to perform upload and returns result.
'''
if send_post_multipart():
	print('success!')
	sys.exit(0)
else:
	print('failure!')
	sys.exit(-1)