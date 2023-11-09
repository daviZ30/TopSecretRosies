# Parte articulo (Antonio Salado)

En mi caso mi parte consiste en la parte de articulo, explicare un poco como hice el diseño de los layouts de los tres modulos de itemcreation, itemdetail y itemlist

## itemcreation

En este modulo consiste en añadir un articulo, para este modulo puse varios TextInputLayout que es mejor que el EditText ya que proporciona mas caracteristicas como que facilita la organización del diseño y la interacción con campos de entrada de texto, en estos TextInputLayout se escribiran los atributos de un articulo que son id, nombre, tasa, descripcion. Luego puse un spinner para el tipo de articulo que ya esta inicializado con un ArrayAdapter que obtendra de un arrayof que tendra producto o servicio y que lo mostrara para elegir en el spinner, por defecto esta en producto. Tambien he creado una clase POJO que es item que se usara cuando implementemos la creacion de un articulo y se usara objetos estaticos de prueba que ya lo comentare en el modulo itemlist, puse tambien un checkbox para saber si un articulo es imponible y por ultimo un FloatingactionButton que basicamente añade un articulo cuando hayamos rellenado los TextInputLayout. Puse un color que se pueda ver bien en las letras cuando se escribe en los TextInputLayout, he optado por un diseño sencillo y que se pueda usar facilmente, tambien he usado una fuente que hemos elegido entre todos que es open_sans que lo he usado en los demas modulos y todo estos componentes los puse en un LinearLayout para que cuando se ejecute en la aplicacion se vea bien.

## itemdetail

Para este modulo basicamente es mostrar los detalles de un articulo sin edicion, consiste lo he dividido en dos CardView que dentro tendrian TextView para los atributos de un articulo sin edicion que serian Articulo que muestra los datos basicos de un articulo id, nombre, tasa y caracteristicas que muestra informacion importante de un articulo como de que tipo es, una despcricion en concreto y si es taxable, dentro de los CardView puse un ConstraintLayout que a su vez para posicionar bien los componentes un par de LinearLayout. Elegi esta combinacion de colores por que visualmente esta bien ademas Alex me ayudo como deberia de ser estos colores y tambien use la misma fuente como en el anterior modulo, tambien puse las esquinas de los CardView redondos que a mi personamente me gusta.

## itemlist

Para este modulo muestra basicamente un listado de articulos con sus atributos, lo que hice aqui es poner un RecyclerView con un id, y luego cree otra vista para como se deberia de ver un articulo dentro del RecycleView, elegi el diseño de esta vista en un CardView con un color para que la letra de vea bien tambien con las esquinas redondas, puse dentro un ConstraintLayout y a su vez un par de LinearLayout para posisionar correctamente los componentes que tiene el CardView, dentro del CardView serian TextView que mostraran cada uno de los atrubutos de un articulo. Tambien cree el Adapter de itemlist para proporcionar los atributos de los articulos al RecyclerView. Despues cree varios objetos estaticos de prueba de la clase POJO en el fragment para ver si el RecyclerView los muestra gracias al Adapter. Se muestra bien los articulos con la vista que hice dentro del RecyclerView y se puede hacer scroll para ver mas vistas de articulo. 

## Dificultades que he tenido

He tenido algunas dificultades como que en el itemcreation cuando lo ejecutaba en la aplicacion cuando esta unido en el nav_graph los componentes se me ponian encima de otro a pesar que cuando lo estoy editando el layout para que se ve bien, asi que puse un LinearLayout para posicionar los componentes de manera correcta, me cuesta un poco utilizar el LinearLayout pero poco a poco empiezo a entenderlo, me paso lo mismo en el itemlist pero tambien tuve otra dificultad en ese modulo y es que cuando cree los iten estaticos de prueba para ver si se creaban bien, estaban muy separados cuando estaba haciendo scroll veia el siguiente elemento y asi sucesivamente y estaba mal diseñado, asi estuve haciendo varias pruebas y llege a una solucion que era reducir de tamaño del ConstraintLayout que tenia asignado el layout de itemlist, lo pruebo ya me sale bien, no estan demasiado separados las vistas de los articulos en el RecyclerView. Tambien me costo un poco como hacer el Adapter del itemlist pero poco a poco iba saliendo bien. Me costo un poco al principio como poner en el nav_graph como poner el fragment de cada modulo, pero ya empece a entender como se hace.

# INVOICE (David Zambrana)
En todos mis layout he implementado un constraintlayout como principal. En ellos me he basado en implementar varios cardview en los cuales distribuyó el contenido. Todo está construido con la misma gama de colores e intentando buscar una similitud en la distribución de los view.
Para el funcionamiento de estos features cree varios fragment en el nav_gragh principal, después implemente varios actions y lo vincule a los botones previamente creados 
## InvoiceCreation
En este layout he implementado varios TextUnputLayout en cada cardview, en ellos he cambiado el tipo de letra por una implementada en el proyecto, también he insertado un icono al inicio. En el último cardview puse un botón, el cual cambie el color principal y su tamaño cambiando el height, y un recyclerview en el cual hay otro cardview en cada fila para mostrar los datos acompañado de un imageview.
![](https://i.postimg.cc/L6ZMm5ZB/creation.png)
## InvoiceDetails
En este layout puse varios TextView varios como lab, los cuales puse en negrita incremente un poco su tamaño, y otros para poner el contenido deseado, todo este contenido tiene el tipo de letra implementado en el proyecto. También podemos ver un RadioGroup con varios RadioButton.
Nuevamente he implementado el mismo reciclerview que en InvoiceCreation.
![](https://i.postimg.cc/RhkyW1n1/details.png)
## InvoiceList
En este layout solo podemos ver el RecyclerView, en el layout de cada fila podemos ver un cardview con el contenido junto a una imagen y un textview. Para el recyclerview implemente el adapter necesario para construirlo pasándole una lista de facturas en la cual cada factura es una dataclass. 
![](https://i.postimg.cc/Kcf54Dtm/list.png)


