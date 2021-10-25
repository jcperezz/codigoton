# jcperezz-codigoton

Mini aplicación con la solución del Reto Programación General (Bancolombia) 2021

## Autor
Juan Carlos Perez Zapata
[Ver Linkedin](www.linkedin.com/in/juan-carlos-perez)

## Configuración conexión DB
Se debe usar una DB MySQL, la configuración de la conexión se encuentra en el archivo config.properties en la carpeta resources

## Instalación y ejecución

Nota: se debe tener instalado Maven y usar Java 1.7 o superior.

1. Descargar o clonar el repositorio
1. En la carpeta raíz del proyecto ejecutar el comando desde el shell:

```shell
mvn clean compile assembly:single
```

3. Al finalizar se debe generar la carpeta `target` y podemos ejecutar el comando:

```shell
java -jar target\jcperezz-codigoton-jar-with-dependencies.jar -f=[PATH_FILE]
```
Donde `[PATH_FILE]` es la ruta completa del archivo de `entrada.txt` a procesar, por ejemplo:

```shell
java -jar target\jcperezz-codigoton-jar-with-dependencies.jar -f=C:\\workspaces\\eclipse-workspace\\jcperezz-codigoton\\src\\main\\resources\\entrada.txt
```

Un ejemplo del contenido del archivo de entrada es:

```
<General>
TC:1
<Mesa 1>
UG:2
RI:500000
<Mesa 2>
UG:1
RF:500000
<Mesa 3>
UG:3
TC:5
RF:10000
<Mesa 4>
UG:1
RF:100000
<Mesa 5>
UG:99
<Mesa 6>
TC:11
RI:10000
```

