package com.mycompany.polaca;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
    ^=94
    *=42
    /=47
    +=43
    -=45
    (=40
    )=41
    216 Ø
    31 
    182 ¶
 */
// C:\Users\luise\Documents\ULSA\Tercer Semestre\Estructura de datos\ecuaciones.txt
public class Main 
{
    public static void main(String[] args) throws FileNotFoundException 
    {
        Listas lst = new Listas();
        String ecuacion;
        char dato;
        Scanner scn = new Scanner(System.in);
        System.out.println("Este es un conversor de notación infija a notación post fija");
        System.out.println("Solo funciona con una ecuación");
        System.out.println("Ingrese la dirección de su archivo .txt");
        String path = scn.nextLine();
        File archivo = new File (path);
        Scanner scanner = new Scanner(archivo);
        while (scanner.hasNextLine())
        {
            ecuacion = scanner.nextLine();
            for (int j = 0; j < ecuacion.length(); j++) 
            {
                dato = ecuacion.charAt(j);
                lst.pushCola(dato);
            }
            lst.polaca();
            lst.vaciarPila_Cola();
            lst.imprimir(lst.inicioCola2);
        }
    }
    
}
public class Nodo {
    char dato;
    Nodo next;
}
public class Listas 
{
    Nodo inicioPila, finPila;
    Nodo inicioCola, finCola;
    Nodo inicioCola2, finCola2;
    public boolean isEmpty(Nodo inicioLista)
    {
        return inicioLista == null;
    }
    
    public Nodo newNodo(char num)
    {
        Nodo nuevo = new Nodo();
        nuevo.dato = num;
        nuevo.next = null;
        return nuevo;
    }
    public void pushPila (char num)
    {
        Nodo nuevo = newNodo(num);
        if (isEmpty(inicioPila))
        {    
            inicioPila = nuevo;
            finPila = nuevo;
        }
        else
        {
            nuevo.next = inicioPila;
            inicioPila = nuevo;
        }
    }
    public void pushCola (char num)
    {
        Nodo nuevo = newNodo(num);
        if (isEmpty(inicioCola))
        {    
            inicioCola = nuevo;   
        }
        else
        {
            finCola.next = nuevo;
        }
        finCola = nuevo;
    }
    public void pushCola2 (char num)
    {
        Nodo nuevo = newNodo(num);
        if (isEmpty(inicioCola2))
        {    
            inicioCola2 = nuevo;
        }
        else
        {
            finCola2.next = nuevo;
        }
        finCola2 = nuevo;
    }
    public void pop ()
    {
        inicioPila = inicioPila.next;
    }
    public char peek (Nodo inicioLista)
    {
        return inicioLista.dato;
    }
    public int precedencia (char operador)
    {
        int precedencia;
        precedencia = switch (operador) 
        {
            case '^' -> 4;
            case '*' -> 3;
            case '/' -> 3;
            case '+' -> 2;
            case '-' -> 2;
            case '(' -> 1;
            case ')' -> 1;
            default -> 0;
        };  
        return precedencia;
    }
    public boolean isOperador (char op)
    {
        if ((op >= 65)&&(op <=90))
        {
            return true;
        }
        else return false;
    }
    public void imprimir (Nodo inicioLista)
    {
        Nodo aux;
        aux = inicioLista;
        while (aux != null)
        {
            if (aux.dato == '(')
            {
                aux=aux.next;
            }
            System.out.print(aux.dato+" ");
            aux=aux.next;
        }
    }
    public void recorrer()
    {
        pushCola2(inicioPila.dato);
        pop();
        if (inicioPila != null)
        {
            recorrer();
        }
    }
    public void polaca ()
    {
        if (isOperador(inicioCola.dato))
        {
            //System.out.println("inserta operador");
            pushCola2(inicioCola.dato);
            inicioCola=inicioCola.next;
        }
        else if (inicioCola.dato == '(')
        {
            pushCola2(inicioCola.dato);
            inicioCola=inicioCola.next;
        }
        else if(inicioCola.dato == ')')
        {
            recorrer();
            inicioCola=inicioCola.next;
        }
        else if(isEmpty(inicioPila) || (precedencia(peek(inicioPila)) == 1))
        {
            //System.out.println("inserta operando");
            pushPila(inicioCola.dato);
            inicioCola=inicioCola.next; 
        }
        else if (precedencia(inicioCola.dato) > (precedencia(peek(inicioPila))))
        {
            //System.out.println("inserta mayor sobre menor");
            pushPila(inicioCola.dato);
            inicioCola=inicioCola.next; 
        }
        else if(precedencia(inicioCola.dato) == precedencia(peek(inicioPila)))
        {
           // System.out.println("inserta iguales");
            pushCola2(inicioPila.dato);
            pop();
            pushPila(inicioCola.dato);
            inicioCola=inicioCola.next; 
        }
        else if((precedencia(inicioCola.dato) < precedencia(inicioPila.dato))&& precedencia(inicioCola.dato)!=0)
        {
           // System.out.println("inserta menor sobre mayor");
            pushCola2(inicioPila.dato);
            pop();
            //System.out.println("entra recursivo");
            polaca(); 
        }   
        if (inicioCola!=null)
        {
            polaca();
        }
    }
    public void vaciarPila_Cola()
    {
        pushCola2(inicioPila.dato);
        inicioPila=inicioPila.next;
        if (inicioPila!=null)
        {
            vaciarPila_Cola();
        }
    }
    /*public void recursivo(Nodo dato)
    {
        Nodo aux = dato.next;
        if (precedencia(dato.dato) <= precedencia(inicioPila.dato))
        {
            System.out.println("aqui");
            pushCola2(inicioPila.dato);
            pop();
            imprimir(inicioCola2);
            System.out.println("");
            imprimir(inicioPila);
            System.out.println("");
            if(aux != null)
            {
                System.out.println("entra de nuevo");
                recursivo(aux);
                pushPila(dato.dato);
            }
        }
    }*/
    public void vaciar(Nodo inicioLista)
    {
    Nodo aux = inicioLista; 
        while (aux!=null) 
        {
            pop();
            if (aux.next != null)
            {
                aux=aux.next;
            }
            
        }
    }
}
