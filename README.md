# proyectokellynjavagit
proyecto de java con git para la clase

El siguiente proyecto se puede utilizar con un git clone en la carpeta de preferencia

```
git clone https://github.com/LuisTull/proyectokellynjavagit.git
```

hay un archivo llamado `.env` en el que se encuentran los campos concernientes a la conexion con la base de datos, el mismo esta en la raiz del proyecto (la carpeta `gitproyectoooo`)

Si el archivo no es clonado se debe de crear el mismo con el nombre citado anteriormente 
> .env

Este programa consta de un login, un registro, vista, edicion y tambien eliminacion de usuarios, la interfaz se hizo en codigo por inconvenientes con el manejador de los forms y los espacios blancos

Casi se me olvida, se debe agregar la libreria para los .env `io.github.cdimascio.dotenv`, el mismo se puede agregar en la seccion de librerias seleccionando maven y colocando `dotenv-java`
La otra libreria es `mysql-connector-j-9.2.0` o simplemente el conector de mysql para java
