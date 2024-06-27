import { AxiosRequestConfig } from "axios";
import { requestBackend } from "../utils/requests";
import { OrderDTO } from "../models/order";

export function findByIdRequest(id: number) {


    const config: AxiosRequestConfig = {
        url: `/orders/${id}`,
        withCredentials: true
    }

    return requestBackend(config);
}


export function placeOrderResquest(cart: OrderDTO) {

    const config: AxiosRequestConfig = {
        method: "POST",
        url: `/orders`,
        withCredentials: true,
        data: cart
    }

    return requestBackend(config);
}
