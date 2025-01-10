import React, { useEffect, useState, useRef } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "../../CSS/storage-page.css"; //Import del file CSS
import IngredientCard from "../IngredientCard.tsx";
import IngredientModal from "./IngredientStorageModal.tsx"
import { FaArrowLeft, FaPlusCircle, FaTrash } from 'react-icons/fa';


interface Ingredient {
    ingredientId: number;
    quantity: number;
    expirationDate: string;
    name: string;
}


interface IngredientDTO {
    ingredientId: number;
    quantity: number;
    username: string;
    expirationDate: string;
}



const StoragePage = () => {
    const [ingredients, setIngredients] = useState<Ingredient[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const token = localStorage.getItem('authToken');
    const navigate = useNavigate();
    const prevIngredientsRef = useRef<Ingredient[]>([]);



    const fetchStorage = async () => {
        if (token == null) throw new Error("the token is null");
        const parsedData = JSON.parse(token);
        const tokenParsed = parsedData.token;
        try {
            setLoading(true);
            const response = await axios.get(`http://localhost:3005/api/storage`, {
                headers: {
                    Authorization: `Bearer ${tokenParsed}`
                }
            }).then((response) => {

                const dtos: IngredientDTO[] = response.data;

                const ingredientList = dtos.map(dto => ({
                    ingredientId: dto.ingredientId,
                    quantity: dto.quantity,
                    expirationDate: dto.expirationDate,
                    name: "",
                  }));
                setIngredients(ingredientList);


            console.log("storage fetched. Response:");
            console.log(response.data);
            fetchAllIngredients();
            console.log("Ingredients fetched");
        }); 
        } catch (err) {
            console.log(err.message)
            setError(err.message);
        } finally {
            setLoading(false);
        }
    };

    const fetchIngredientDetails = async (ingredientId: number) => {
        if (token == null) throw new Error("the token is null");
        const parsedData = JSON.parse(token);
        const tokenParsed = parsedData.token;
        try {
            const response = await axios.get(`http://localhost:3004/recipes-ingredients/ingredients/search-by-id/${ingredientId}`, {
                headers: {
                    Authorization: `Bearer ${tokenParsed}`
                }
            });
            console.log(response.data);
            return response.data;
        } catch (err) {
            setError('Error fetching ingredient details');
            return null;
        }
    };



    const fetchAllIngredients = async () => {
        if (ingredients.length !== 0) {
            const ingredientDetails = await Promise.all(
                ingredients.map(async (ingredient) => {
                    const ingredientDetails = await fetchIngredientDetails(ingredient.ingredientId);
                    if (ingredientDetails) {
                        return {
                            ...ingredient,
                            name: ingredientDetails.name,
                        };
                    }
                    return ingredient;
                })
            );
            setIngredients(ingredientDetails);
        }
    };

    const handleDeleteIngredient = async (ingredientId: number) => {
        if (token == null) throw new Error("the token is null");
        const parsedData = JSON.parse(token);
        const tokenParsed = parsedData.token;
        try {
            const response = await axios.delete(`http://localhost:3005/api/storage/deleteIngredient/${ingredientId}`, {
                headers: {
                    Authorization: `Bearer ${tokenParsed}`
                }
            });
            console.log("Ingredient deleted", response);
            setIngredients((prevIngredients) => prevIngredients.filter((ingredient) => ingredient.ingredientId !== ingredientId));
        } catch (error) {
            console.error("Error deleting ingredient", error);
        }
    };

    const handleAddIngredientClick= () => {
        setIsModalOpen(true);
    };

    const handleCloseModal = () => {
        setIsModalOpen(false);
    };

    const handleAddIngredientToList = () => {
        const prevIngredients = prevIngredientsRef.current;

        if (ingredients.length > 0 && !error && JSON.stringify(prevIngredients) !== JSON.stringify(ingredients)) {
            fetchAllIngredients();
        }
        prevIngredientsRef.current = ingredients;
        setIsModalOpen(false);
    };


    useEffect(() => {
        fetchStorage();
        console.log("ingredients", ingredients);
    }, [token]);

    useEffect(() => {
        const prevIngredients = prevIngredientsRef.current;

        if (ingredients.length > 0 && !error && JSON.stringify(prevIngredients) !== JSON.stringify(ingredients)) {
            fetchAllIngredients();
        }
        prevIngredientsRef.current = ingredients;

    }, [ingredients, error]);

    if (loading) return <div>Loading...</div>;
    if (error) return <div>Error: {error}</div>;



    return (
        <div className="shopping-list-wrapper">
            <div className="header">
                <button className="back-button" onClick={() => navigate(-1)}>
                    <FaArrowLeft /> Back
                </button>
                <div className="actions">
                    <button className="action-button" onClick={handleAddIngredientClick}>
                        <FaPlusCircle className="icon" /> Add Ingredient
                    </button>
                </div>
            </div>
            
            <h1 className="center-text">Storage</h1>
            <hr />
            { ingredients.length!==0 ? ( 
                <div>
                    <ul>
                        {ingredients.map((ingredient, index) => (
                            <li key={index}>
                                <IngredientCard key={ingredient.ingredientId} id={ingredient.ingredientId} name={ingredient.name as string} quantity={ingredient.quantity} expirationDate={ingredient.expirationDate} onDelete={handleDeleteIngredient} />
                            </li>
                        ))}
                    </ul>
                </div>
            ) : (
                <h1 className="center-text">No ingredients in this storage</h1>
            )}
            {isModalOpen && (
                <IngredientModal onClose={handleCloseModal} onAdd={handleAddIngredientToList}/>
            )}
            
        </div>
    );





}

export default StoragePage;