package no.oslomet.cs.algdat.Oblig3;

import java.util.*;

public class SBinTre<T> {
    private static final class Node<T>   // en indre nodeklasse
    {
        private T verdi;                   // nodens verdi
        private Node<T> venstre, høyre;    // venstre og høyre barn
        private Node<T> forelder;          // forelder

        // konstruktør
        private Node(T verdi, Node<T> v, Node<T> h, Node<T> forelder) {
            this.verdi = verdi;
            venstre = v;
            høyre = h;
            this.forelder = forelder;
        }

        private Node(T verdi, Node<T> forelder)  // konstruktør
        {
            this(verdi, null, null, forelder);
        }

        @Override
        public String toString() {
            return "" + verdi;
        }

    } // class Node

    private Node<T> rot;                            // peker til rotnoden
    private int antall;                             // antall noder
    private int endringer;                          // antall endringer

    private final Comparator<? super T> comp;       // komparator

    public SBinTre(Comparator<? super T> c)    // konstruktør
    {
        rot = null;
        antall = 0;
        comp = c;
    }

    public boolean inneholder(T verdi) {
        if (verdi == null) return false;

        Node<T> p = rot;

        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) p = p.venstre;
            else if (cmp > 0) p = p.høyre;
            else return true;
        }

        return false;
    }

    public int antall() {
        return antall;
    }

    public String toStringPostOrder() {
        if (tom()) return "[]";

        StringJoiner s = new StringJoiner(", ", "[", "]");

        Node<T> p = førstePostorden(rot); // går til den første i postorden
        while (p != null) {
            s.add(p.verdi.toString());
            p = nestePostorden(p);
        }

        return s.toString();
    }

    public boolean tom() {
        return antall == 0;
    }

    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi, "Ulovlig med nullverdier!");

        Node<T> p = rot, q = null;               // p starter i roten
        int cmp = 0;                             // hjelpevariabel

        while (p != null)       // fortsetter til p er ute av treet
        {
            q = p;                                 // q er forelder til p
            cmp = comp.compare(verdi,p.verdi);     // bruker komparatoren
            p = cmp < 0 ? p.venstre : p.høyre;     // flytter p
        }

        // p er nå null, dvs. ute av treet, q er den siste vi passerte

        p = new Node<>(verdi,q);                   // oppretter en ny node

        if (q == null) rot = p;                  // p blir rotnode
        else if (cmp < 0) q.venstre = p;         // venstre barn til q
        else q.høyre = p;                        // høyre barn til q

        if (q != null) p.forelder = q; // Hvis p ikke er rotnoden setter sin forelder lik q

        antall++;                                // én verdi mer i treet
        return true;                             // vellykket innlegging
    }

    public boolean fjern(T verdi) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public int fjernAlle(T verdi) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public int antall(T verdi) {
        if (verdi == null) return 0; // null er ikke i treet

        int antall = 0; // teller antall forekomster av verdi

        if (!tom()) {
            Node<T> p = rot; // setter p lik roten
            int cmp; // hjelpevariabel for komparatoren

            while (p != null) { // bruker samme while-løkka som leggInn()-metoden
                if (verdi.equals(p.verdi)) antall++;
                cmp = comp.compare(verdi,p.verdi);     // bruker komparatoren
                p = cmp < 0 ? p.venstre : p.høyre;     // flytter p
            }
        }
        return antall;
    }

    public void nullstill() {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    private static <T> Node<T> førstePostorden(Node<T> p) {
        while (true)
        {
            if (p.venstre != null) p = p.venstre;
            else if (p.høyre != null) p = p.høyre;
            else return p;
            // første node i postorden er noden lengst til venstre
            // som har ingen barn
        }
    }

    private static <T> Node<T> nestePostorden(Node<T> p) {
        // hvis p har ingen forelder er den roten som har ingen neste
        // node i postorden
        if (p.forelder == null) return null;

        // hvis p er høyre barn av sin forelder eller forelderens høyre er
        // tom, så er forelder den neste node i postorden
        Node<T> f = p.forelder;
        if (f.høyre == null || f.høyre == p) return f;

        // I alle andre tilfeller, finn barnet lengst til venstre i
        // høyre subtreet til forelderen
        Node<T> n = f.høyre;
        while (n.venstre != null) n = n.venstre;
        return n;
    }

    public void postorden(Oppgave<? super T> oppgave) {
        Node<T> p = rot;
        while (true) // tar utgangspunkt fra førstePostorden-metoden
        {
            if (p.venstre != null) p = p.venstre;
            else if (p.høyre != null) p = p.høyre;
            else break; // hopper ut av while-løkka
        }

        // går gjennom hele treet i postorden helt til p er null
        while (p != null) {
            oppgave.utførOppgave(p.verdi);
            p = nestePostorden(p);
        }
    }

    public void postordenRecursive(Oppgave<? super T> oppgave) {
        postordenRecursive(rot, oppgave);
    }

    private void postordenRecursive(Node<T> p, Oppgave<? super T> oppgave) {
        if (p.venstre != null) postordenRecursive(p.venstre,oppgave);
        if (p.høyre != null) postordenRecursive(p.høyre,oppgave);
        oppgave.utførOppgave(p.verdi);
    }

    public ArrayList<T> serialize() {
        if (tom()) return null; // hvis tabellen er tom returnerer vi null

        ArrayList<T> arr = new ArrayList<>();
        Queue<Node> kø = new LinkedList<>();
        kø.add(rot); // legger til roten til køen

        while (!kø.isEmpty()) { // går gjennom hele treet
            Node<T> temp = kø.remove(); // flytter første node i køen til temp
            arr.add(temp.verdi);        // legger til temp til array listet
            if (temp.venstre != null) kø.add(temp.venstre); // hvis temp har venstre barn legge til i køen
            if (temp.høyre != null) kø.add(temp.høyre); // hvis temp har høyre barn legge til i køen
        }
        return arr;
    }

    static <K> SBinTre<K> deserialize(ArrayList<K> data, Comparator<? super K> c) {
        if (data.size() == 0) return null; // hvis array liste er tom returnerer det null

        SBinTre<K> nyTre = new SBinTre<>(c);
        nyTre.rot = new Node(data.get(0),null); // roten er første noden i liste med forelder lik null

        for (int i = 1; i < data.size(); i++) { // går gjennom hele array listet
            nyTre.leggInn(data.get(i)); // legger inn nodene
        }
        return nyTre;
    }

    public static void main(String[] args) {
        Integer[] a = {4,7,2,9,4,10,8,7,4,6};
        SBinTre<Integer> tre = new SBinTre<>(Comparator.naturalOrder());
        for (int verdi : a) { tre.leggInn(verdi); }

        System.out.println(tre.antall());      // Utskrift: 10
        System.out.println(tre.antall(5));     // Utskrift: 0
        System.out.println(tre.antall(4));     // Utskrift: 3
        System.out.println(tre.antall(7));     // Utskrift: 2
        System.out.println(tre.antall(10));    // Utskrift: 1
    }

} // ObligSBinTre
