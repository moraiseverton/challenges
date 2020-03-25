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


## Pyenv Installation

```shell
$ brew update
$ brew install pyenv
$ echo 'eval "$(pyenv init -)"' >> ~/.bash_profile
```

## Python Installation

```shell
$ pyenv install
```

## Pipenv Installation

```shell
$ brew install pipenv
$ echo 'eval "$(pipenv --completion)"' >> ~/.bash_profile
```

## Python dependencies

Dependencies can be downloaded

```shell
pipenv sync
```

Additional dependencies can be added via

```shell
pipenv install {pip_package_name}
```

## Javascript Dependencies

From the frontend folder, dependencies can be downloaded

```shell
npm install
```

Additional dependencies can be added via

```shell
npm install {package_name} --save-exact
```
