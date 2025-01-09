import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import "../../CSS/RecipeInfoPage.css";
import axios from "axios";
import { useLocation } from "react-router-dom";

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
  const location = useLocation();
  const [isFavorite, setIsFavorite] = useState(false);
  const { recipeId } = useParams(); // Extract recipe ID from the URL
  const [recipe, setRecipe] = useState<Recipe | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const token = localStorage.getItem('authToken');

  const isAlreadyFav = async (id?: string) => {
    if(token == null) throw new Error("the token is null");
    const parsedData = JSON.parse(token);
    const tokenParsed = parsedData.token;
    try {

      const response = await axios.get(`http://localhost:3001/profiles/profile/favorites`, {
        headers: {
          Authorization: `Bearer ${tokenParsed}`,
        },
      });
      console.log("Recipe checked", response);
      const recipeIds = response.data.recipeIds;
      const numericId = Number(id);
      const isFavorite = recipeIds.includes(numericId);
      console.log("Recipe is favorite? ", isFavorite);

      return isFavorite;

    } catch (error) {
      console.error("Error checking recipe", error);
      return true;
    }
  };


  useEffect(() => {
    const checkIfFavorite = async () => {
      const result = await isAlreadyFav(recipeId);
      setIsFavorite(result);
    };

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

    checkIfFavorite();
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
  const { calories, carbs, fat, protein } = nutrition

  const handleAdd = async (id: string) => {
    if(token == null) throw new Error("the token is null");
    const parsedData = JSON.parse(token);
    const tokenParsed = parsedData.token;
    try {

      const response = await axios.post(`http://localhost:3001/profiles/profile/favorites/${id}`,{}, {
        headers: {
          Authorization: `Bearer ${tokenParsed}`,
        },
      });
      console.log("Recipe added", response);

    } catch (error) {
      console.error("Error adding recipe", error);
    }
  };



  
  return (
    <div className="recipe-info-wrapper">
        <div className="recipe-details">
        <h1 className="recipe-details__title">{title}</h1>
        <img
            className="recipe-details__image"
            src={image || ""}
            alt={title || "Recipe Image"}
        />
        {(location.pathname.includes("/recipes/info") && !isFavorite) &&(
            <button
              onClick={() => handleAdd(recipe.id)}
              className="create-button"
            >
              Add to favourites
            </button>
      )}

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
