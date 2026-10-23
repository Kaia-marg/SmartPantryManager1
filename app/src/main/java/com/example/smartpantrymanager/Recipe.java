package com.example.smartpantrymanager;

public class Recipe {
    private int id;
    private String name;
    private String category;
    private String ingredients;
    private String instructions;

    public Recipe(int id, String name, String category, String ingredients, String instructions) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public String getIngredients() { return ingredients; }
    public String getInstructions() { return instructions; }
}