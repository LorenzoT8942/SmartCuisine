import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import "../../CSS/RecipeInfoPage.css";
import axios from "axios";

interface Ingredient {
    amount: number;
    unit: string;
    name: string;
  }
  
  
  interface Recipe {
    id: string;
    title: string;
    servings: number;
    image: string;
    readyInMinutes: number;
    summary: string;
    extendedIngredients: Ingredient[];
    nutrition: {
      calories: number;
      carbs: string;
      fat: string;
      protein: string;
    };
  }

const RecipeDetailsPage = () => {
  const { recipeId } = useParams(); // Extract recipe ID from the URL
  const [recipe, setRecipe] = useState<Recipe | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const token = localStorage.getItem('authToken');

  useEffect(() => {
    // Fetch recipe details from the API
    const fetchRecipeDetails = async () => {
      try {
        if(token == null) throw new Error("the token is null");
        const parsedData = JSON.parse(token);
        const tokenParsed = parsedData.token;


        const response = await axios.get(`http://localhost:3004/recipes-ingredients/recipes/info/${recipeId}`, {
            headers: {
              Authorization: `Bearer ${tokenParsed}` // Bearer token for authentication
            }
          });
        const data = response.data;
        console.log(data);
        console.log(data.toString());
        setRecipe(data);
        setLoading(false);
      } catch (err) {
        setError(err.message);
        setLoading(false);
      }
    };

    fetchRecipeDetails();
  }, [recipeId]);

  if (loading || !recipe) return <div>Loading...</div>;
  if (error) return <div>Error: {error}</div>;

  const {
    id,
    title,
    servings,
    image,
    readyInMinutes,
    summary,
    extendedIngredients,
    nutrition,
  } = recipe;

  // Extract relevant nutrients
  const { calories, carbs, fat, protein } = nutrition;
  
  return (
    <div className="recipe-info-wrapper">
        <div className="recipe-details">
        <h1 className="recipe-details__title">{title}</h1>
        <img
            className="recipe-details__image"
            src={image || ""}
            alt={title || "Recipe Image"}
        />

        <div className="recipe-details__info">
            <h2>Preparation Time</h2>
            <p>{readyInMinutes} minutes</p>

            <h2>Instructions</h2>
            <p>{summary}</p>

            <h2>Ingredients</h2>
            <ul>
            {extendedIngredients.map((ingredient, index) => (
                <li key={index}>
                {ingredient.amount} {ingredient.unit} {ingredient.name}
                </li>
            ))}
            </ul>

            <h2>Nutritional Values</h2>
            <ul>
              <li>Calories: {calories}</li>
              <li>Carbohydrates: {carbs}</li>
              <li>Fat: {fat}</li>
              <li>Protein: {protein}</li>
            </ul>
        </div>
        </div>
    </div>
  );
};

export default RecipeDetailsPage;
