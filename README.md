# ResQ Take-home Template

This project has scaffolding for both a frontend react project and a backend django project.
Depending on the take home you have been given you may need only one or both.

The scaffolding has been created with the following dependencies

- pyenv - python installation
- pipenv - python package installation
- npm - javascript package installation

It is not required to use the full ecosystem as specified BUT the project should be able to be installed via the following commands:

*Backend*

```shell
pipenv sync
pipenv run python backend/manage.py runserver
```

*Frontend*

```shell
cd frontend
npm install
npm start
```

# Installation Instructions

## 2. Install pyenv

-   run the following in terminal

```shell
$ brew update
$ brew install pyenv
$ echo 'eval "$(pyenv init -)"' >> ~/.bash_profile
```

## 3. Install pipenv

-   run the following commands in terminal

```shell
$ brew install pipenv
$ echo 'eval "$(pipenv --completion)"' >> ~/.bash_profile
```

## 4. Install Postgres

-   run the following command in terminal to install postgres

```shell
$ brew install postgres
```

-   run the following command in terminal to run postgres as a service

```shell
$ brew services start postgres
```

-   create the resq database with psql

```shell
$ psql postgres
postgres=# CREATE DATABASE resq;
```

## 5. Install WeasyPrint dependencies

-   For installing WeasyPrint dependencies `brew install cairo pango gdk-pixbuf libffi`

## 6. Install Python & dependencies

-   If you're on Mac, you need to do the following (you may have to do this every time you run pip install):

### Mac OS Catalina (10.15.x) users

```shell
brew install curl-openssl # You only need to do this once
export PYCURL_SSL_LIBRARY=openssl
export PYCURL_CURL_CONFIG=/usr/local/opt/curl-openssl/bin/curl-config;export LDFLAGS='-L/usr/local/opt/openssl/lib -L/usr/local/opt/c-ares/lib -L/usr/local/opt/nghttp2/lib -L/usr/local/opt/libmetalink/lib -L/usr/local/opt/rtmpdump/lib -L/usr/local/opt/libssh2/lib -L/usr/local/opt/openldap/lib -L/usr/local/opt/brotli/lib';export CPPFLAGS=-I/usr/local/opt/openssl/include;pip install pycurl --compile --no-cache-dir
```

-   run the following commands in terminals

```shell
$ pipenv sync --dev
```

### Older Versions of MacOS

```shell
pipenv shell
export PYCURL_SSL_LIBRARY=openssl
export CPPFLAGS=-I/usr/local/opt/openssl/include
export LDFLAGS=-L/usr/local/opt/openssl/lib
pip install pycurl==7.43.0.0 --global-option=build_ext --global-option="-L/usr/local/opt/openssl/lib" --global-option="-I/usr/local/opt/openssl/include"
```

-   run the following commands in terminals

```shell
$ pipenv sync --dev
```

## 7. Run migrations & seed data

```shell
$ pipenv run python manage.py migrate
$ pipenv run python manage.py seed_db
```

## 8. Start the development server
