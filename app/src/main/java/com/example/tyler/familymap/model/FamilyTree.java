package com.example.tyler.familymap.model;

import android.graphics.PorterDuff;

/**
 * Created by Tyler on 8/4/2016.
 */
public class FamilyTree {


    private Node root;

    /**
     * Creates the family tree for the person. Ideally this person is ME.
     * @param person
     */
    public FamilyTree(Person person)
    {
        root = new Node(person);
        populateTree(root);
    }

    public void populateTree(Node node){
        if (node.getFather() != null)
        {
            populateTree(node.getFather());
            return;
        }
        if (node.getMother() != null)
        {
            populateTree(node.getMother());
            return;
        }
    }

    public int size() {
        return(size(root));
    }
    private int size(Node node) {
        if (node == null) return(0);
        else {
            return(size(node.father) + 1 + size(node.mother));
        }
    }

    private class Node{
        private Person person;
        private Node father;
        private Node mother;

        public Node(Person person){
            this.person = person;

            if (person.getFather() != null)
            {
                this.father = new Node(ModelData.getInstance().personIDMap.getPersonIDmap().get(person.getFather()));
            }
            if (person.getMother() != null)
            {
                this.mother = new Node(ModelData.getInstance().personIDMap.getPersonIDmap().get(person.getMother()));
            }
        }

        public Node getFather() {
            return father;
        }

        public Node getMother() {
            return mother;
        }
    }




}
