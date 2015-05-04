# Challenge 10 - HSL

[LINK TO ORIGINAL](https://contest.tuenti.net/Challenges?id=10)

There are smart ass developers out there! And some of them are even able to hyper-optimize code. Why read things from disk?!?! Disks are slow!!!!

**[http://54.83.207.93:5252](http://54.83.207.93:5252)**

### Input

This problem has no input

### Output

The key

**NOTE:** In this level, the output of the submit phase is the same one as the test phase.

```python
#!/usr/bin/env python

# this is an optimized HTTP server app
# we avoid re reading config on sub sequent requests by using a nice trick

from BaseHTTPServer import HTTPServer, BaseHTTPRequestHandler
from SocketServer import ThreadingMixIn
import threading
import urlparse
import json
import cgi
import time
import Cookie
import hashlib
import urllib
import MySQLdb

CONFIG_FILE="hsl.config"

class HSLRequestHandler(BaseHTTPRequestHandler):
  def __init__(self, *args):
    self._load_templates()
    self.secret = self._get_secret()

    BaseHTTPRequestHandler.__init__(self, *args)

  # Disable logging DNS lookups
  def address_string(self):
    return str(self.client_address[0])

  def _load_templates(self):
    self.template_files = {'login_form': 'templates/login_form.html',
                           'show_key': 'templates/show_key.html',
                           'invalid_credentials': 'templates/invalid_credentials.html'}
    self.templates = {}
    for k,v in self.template_files.iteritems():
      self.templates[k] = open(v, "r").read()

  def _get_secret(self):
    secret_file = '/etc/passwd'
    # 1992-04-08: cool secret store
    # we are assuming first line contains the root pass :)
    # TODO: review after the shadow password suite from J. Haugh is ported to Linux
    return open(secret_file).readline().split(":")[1]


class Handler(HSLRequestHandler):

    def do_GET(self):
        self.send_response(200)
        config = self._get_config_from_cookie(self.headers)
        if config:
          parsed_config = self._parse_config(config)
          print parsed_config
        else:
          config = self._get_config_from_file(CONFIG_FILE)

          cookie = Cookie.SimpleCookie()
          cookie['config'] = urllib.quote(config)
          cookie['sig'] = self._generate_sig(urllib.quote(config))
          for v in cookie.values():
            self.send_header('Set-Cookie', v.output(header='').lstrip())
        self.end_headers()

        message = self.templates['login_form']

        self.wfile.write(message)
        self.wfile.write('\n')
        return
    def do_POST(self):
      self.send_response(200)
      self.end_headers()
      length = int(self.headers.getheader('content-length'))

      parsed_config = None

      config = self._get_config_from_cookie(self.headers)
      if config:
        parsed_config = self._parse_config(config)
        print parsed_config
      else:
        config = self._get_config_from_file(CONFIG_FILE)

        cookie = Cookie.SimpleCookie()
        cookie['config'] = urllib.quote(config)
        cookie['sig'] = self._generate_sig(urllib.quote(config))
        for v in cookie.values():
          self.send_header('Set-Cookie', v.output(header='').lstrip())
      self.end_headers()

      postvars = cgi.parse_qs(self.rfile.read(length), keep_blank_values=1)

      if 'user' in postvars and 'password' in postvars:
        authenticated = self._auth(parsed_config, postvars['user'][0], postvars['password'][0])

        if authenticated:
          message = self.templates['show_key']
        else:
          message = self.templates['invalid_credentials']

      else:
        message = 'you missed something'

      self.wfile.write(message)
      self.wfile.write('\n')
      return


    def _get_config_from_cookie(self, headers):
      if headers.has_key('cookie'):
        cookie = Cookie.SimpleCookie(headers.getheader("cookie"))
        config = cookie.get("config")
        sig = cookie.get("sig")
        if self._generate_sig(config.value) != sig.value:
          return None
        else:
          return config.value

      else:
        return None


    def _generate_sig(self, data):
      data = data.lower()
      return hashlib.md5(self.secret + data).hexdigest()


    def _parse_config(self, config):
      return urlparse.parse_qs(urlparse.unquote(config))


    def _get_config_from_file(self, f):
      with open(f) as ff:
        return ff.read()[:-1]


    def _auth(self, parsed_config, user, password):
      db = MySQLdb.connect(host=parsed_config['db_host'][0],
                           user=parsed_config['db_user'][0],
                           passwd=parsed_config['db_passwd'][0],
                           db=parsed_config['db_name'][0])
      cur = db.cursor()
      cur.execute("select * from users where name=%(user)s and passwd=%(passwd)s", {'user': user, 'passwd': password})
      row = cur.fetchone()
      print row
      db.close()
      return row


class ThreadedHTTPServer(ThreadingMixIn, HTTPServer):
    """Handle requests in a separate thread."""

if __name__ == '__main__':
    server = ThreadedHTTPServer(('0.0.0.0', 5252), Handler)
    print 'Starting server, use <Ctrl-C> to stop'
    server.serve_forever()
```

---

### How I solved it

I don't know if the way I did it was the right way to solve this challenge, but there it goes.

First of all, at L32 we can see that there exist 3 "return pages":

1. A login form
2. A "show_key" page, the one we will want to be shown
3. An "invalid credentials" one

As we will be mainly doing POST requests, I will skip the `do_GET` part, as we won't even browse the page at all.

At line 77 we can see that the `config` variable is loaded from `_get_config_from_cookie(self.headers)`. The first that function does is to check if there's a `Cookie` header, so now we know that we will need to mess with cookies.

After checking the Cookie header, it gets two cookies: `config` and `sig`.

If we try to make a POST request without setting that cookies, we will see that the response contains a message like:

![Response](http://i.imgur.com/4QIq5Ld.jpg)

So now we have somewhere to start.

The function also checks (L114) if the value of the `sig` cookie equals the return of `_generate_sig(config.value)`.

The `_generate_sig` function receives a `data` argument, that in this case will be the value of the `config` cookie. Then, it's converted to lowercase, and calculates the MD5 hash of the concatenation of `self.secret` and the received argument.

So, now we see that the `sig` cookie value must be equal to the MD5(self.secret + config.value).

But what's the `self.secret` value? We have two ways of discovering it:

1. Try to see if the current `sig` value is a known reversed MD5
2. Take a look about how it's generated

#### 1

Visiting [this link](http://md5.gromweb.com/?md5=2e3408c61118229d8e3c9b380a7ab2bb) we can see that is an already reversed MD5 hash, that corresponds to

```
xapp_name%3dhsl%26db_name%3dhsl%26db_user%3dhsl%26db_passwd%3dcbxn2a%40-q_av%407d_%26db_host%3dlocalhost
```

Does this content look familiar? Yes, it's the content of the `config` cookie, but with an `x` at the start... so now we know that `self.secret` is `x`


#### 2

The `self.secret` variable is loaded at line 23, and its initialized to the return value of the `_get_secret()` function. That function loads the `/etc/passwd` file (and we are told that the first line contains the root password), reads the first line, makes a split by ':' and gets the second element (index 1).

For those who are not familiar to the `/etc/passwd` file, it stores the users and passwords in a *NIX system in a format like

```
username:password:uid......
```

If there's an x at the password field, it indicates that the encrypted password is stored at the `/etc/shadow` file.
By now, we don't care, as now we can generate our own `config` and `sig` cookies, and make the system take them as valid cookies.

> #### For more information
> If you want more information about the `/etc/passwd` file, I recommend you to read [this post](http://www.cyberciti.biz/faq/understanding-etcpasswd-file-format/)

We were on L77, and as now `config` will not be `None`, the server will enter into the if and execute L79, which will parse the `config` cookie value.

By taking a look to the value of the config cookie, we can see that it stores information in a key:value format.

Here's the content made more human-readable (URI-escaped and formatted)

```
app_name=hsl
db_name=hsl
db_user=hsl
db_passwd=CBXN2a@-Q_aV@7D_
db_host=localhost
```

> #### NOTE
> `app_name` will never be used at all

And if we keep following the server execution, we can see that at L91 it parses the POST body (user and password), and then at line 94 it makes a call to `self._auth()`, passing it the parsed config, the sent user and the sent password (those two last, remember, from the Body of the POST request).

The `_auth` function creates a MySQL database connection with the parsed_config values (remember, those came from the config.value cookie), and then executes a query where it looks for a table *users*, with attributes *name* and *passwd*, and checks if there exists a row with the values we submitted at the Body of the POST request. It's also protected against SQLi.

Now, here it comes what I did.

I created a local database with a table called *users*, two columns named *name* and *passwd*, and added a sample user.

Now, I know that I can modify the `config` cookie value, and I also know how to generate the corresponding `sig` cookie (MD5(x + config.value)). So, if I set the db params according to my personal connection, instead of theirs, I can force their server to connect to my personal database, make the check of a user and a password I certainly know that will exist (because I just added it), and as it will return a value, the server will return the `show_key.html` page.
