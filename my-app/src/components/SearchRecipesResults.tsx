import React, { useEffect, useState } from "react";
import RecipeList from "./RecipeList.tsx";
import SearchBar from "./SearchBar.tsx";
import { useLocation } from "react-router-dom";
import axios from "axios";
import "../CSS/searchRecipesResults.css";

const SearchRecipesResults = () => {

  const [recipes, setRecipes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const location = useLocation();
  var initQuery = new URLSearchParams(location.search).get("query");
  const [searchValue, setSearchValue] = useState(initQuery);
  const token = localStorage.getItem('authToken');

  const handleSearch = async (query) => {
    setSearchValue(query);
    if(token == null) throw new Error("the token is null");
    if (!query) return; // Avoid fetching if query is empty
    const parsedData = JSON.parse(token);
    const tokenParsed = parsedData.token;
    try {
      setLoading(true);
      const response = await axios.get(`http://localhost:3004/recipes-ingredients/recipes/search-by-name?name=${query}`, {
        headers: {
          Authorization: `Bearer ${tokenParsed}` // Bearer token for authentication
        }
      });

      const data = response.data;
      setRecipes(data);
      console.log(recipes);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    console.log("profile of token "+ token);
    handleSearch(searchValue);
  }, []);
  
  return (
    <div className="search-results-wrapper">
      <SearchBar onSearch={handleSearch} />
      <h1 id="results-message">Search Results for "{searchValue}"</h1>
      {recipes.length > 0 ? (
        <RecipeList recipes={recipes} />
      ) : (
        <p>No recipes found. Try another search!</p>
      )}
    </div>
  );
};

export default SearchRecipesResults;
