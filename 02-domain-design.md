# Domain Design

This document defines the business domain of the e-commerce and other management platform. It describes the main business areas, theis responsibilities, core domain entities, business rules, and the events that will be used to communicate relevant changes between microservices

The purpose of this document is to eestablish a clear understanding of the business domain before implementing the application

# Table of Contents

1. Business Overview
2. Main Business Flow
3. Bounded Contexts
4. Core Domain Entities
5. Order Lifecycle
6. Domain Events
7. Domain Design Principles

# 1. Business Overview

The platform is an e-commerce system focused on technology products.

Customers can browse the product catalog, add products to their shopping cart, place orders, andd track their orders throughout their lifecycle.

Administrators can manage products, categories, inventory and orders.

The platform is designed around independent business contexts, each represented by a dedicated microservice.

# 2. Main Business Flow

The main customer journey is: 

```
Customer
   │
   ├── Register / Login
   │
   ▼
Browse Product Catalog
   │
   ├── Search Products
   ├── Filter Products
   └── View Product Details
   │
   ▼
Add Products to Cart
   │
   ▼
Confirm Cart
   │
   ▼
Create Order
   │
   ▼
Check Inventory
   │
   ├── Stock Available
   │       │
   │       ▼
   │   Reserve Stock
   │       │
   │       ▼
   │   Process Payment
   │       │
   │    ┌──┴──┐
   │    │     │
   │ Success  Failure
   │    │     │
   │    ▼     ▼
   │  Paid   Cancelled
   │    │
   │    ▼
   │ Notification
   │
   └── Stock Unavailable
           │
           ▼
      Order Rejected
```

The payment process will initially be simulated. A dedicated Payment Service may be introduced in a later phase if it provides additional value for the project.

# 3. Bounded Contexts

The system will be divided into several bounded contexts. Each context represents a specific business responsibility and will initially be implemented as an independent microservice

## User Context
Responsible for:
- User registration
- Authentication
- Authorization
- User profiles
- User roles

The User Context does not manage orders, products, or inventory

## Product Context
Responsible for:
- Product catalog
- Product information
- categories
- Product search and filtering
- Product images

The Product Context is responsible for product informatino, but not for managing orders or inventory operations

## Oder Context
Responsible for:
- Shopping carts
- Orders
- Order items
- Order status
- Order totals
- Order lifecycle

The Order Context must not directly access the Product Service database or the Inventory Service database.

When an order is created, the information required to preserve the order's historical stat will be stored within the Order Context.

For example, an OrderItem will contain the product name and price at the time the order was placed. This prevents fufure product changes from modifying historical orders.

## Inventory Context
Responsible for:
- Product stock
- Stock availability
- Stock reservations
- Stock releases

The inventory Context is the only context responsible for modifying inventory quantities.

This separation will allow us to address concurrency problems such as multiple customers attempting to purchase the last available units of a products

## Notification Context
Responsible for:
- Customer notification
- Order confirmation notifications
- Order status notifications
- Order cancellation notifications

The Notification Context will primarilly resat to integration events received through Kafka rather than being directly controlled by other microservices

For example:
````
OrderCreatedEvent
       │
       ▼
     Kafka
       │
       ▼
Notification Service
       │
       ▼
Order confirmation
```

# 4. Core Domain Entities

The following entities have been identified at the initial design stage:

## User
- id
- username
- email
- password
- role
- ...

The User entity belongs exclusively to the User Context.

## Product
- id
- name
- description
- price
- category
- ...

The Product entity belongs to the Product Context.

## Category
- id
- name
- description

A category groups related products within the Product Context.

## Cart
- id
- userId
- items

The Cart represents the products currently selected by a customer before an order is placed.

## CartItem
- productId
- quantity
- ...

A CartItem represents a product and its selected quantity within a cart.

## Order
- id
- userId
- status
- items
- total
- ...

The Order represents a confirmed purchase and its lifecycle

## OrderItem
- productId
- productName
- unitPrice
- quantity

An OrderItem represents a product inclded in an order.

The product name and unit price will be stored as part of the order so that historical orders remain consistent even if the original product is later modified.

## InventoryItem
- productId
- availableQuantity
- reservedQuantity

An inventoryItem represents the inventory information associated with a product.

The Inventory Service is the owner of this data.

# 5. Order Lifecycle

An order will follow a defined lifecycle.

The initial state model is:
```
             ┌──────────┐
             │ PENDING  │
             └────┬─────┘
                  │
                  ▼
             ┌──────────┐
             │   PAID   │
             └────┬─────┘
                  │
                  ▼
             ┌──────────┐
             │ SHIPPED  │
             └────┬─────┘
                  │
                  ▼
             ┌───────────┐
             │ DELIVERED │
             └───────────┘
```
Cancellation can occur from specific states:

PENDING ───────► CANCELLED

PAID ──────────► CANCELLED

The exact cancellation rules will be defined before implementing the Order Service.

For example, cancelling a paid order may require a simulated refund process.

# 6. Domain Events

The domain may generate events when significant business actions occur.

Initial candidates include:
- UserRegisteredEvent
- OrderCreatedEvent
- OrderPaidEvent
- OrderCancelledEvent
- OrderShippedEvent
- OrderDeliveredEvent
- StockReservedEvent
- StockReleasedEvent

Not every domain event necessarily needs to become a Kafka message

A distinction will be made between:
- Domain Events: Represents something meaningful that happened within a bounded context.
- Integration Events: Events published externally so that other microservices can react to a business event.

For example:
```
OrderCreated
     │
     ▼
Domain Event
     │
     ▼
Order Service
     │
     ▼
Integration Event
     │
     ▼
Kafka
     │
 ┌───┴──────────────┐
 ▼                  ▼
Inventory        Notification
Service           Service
```
This distinction will become particularly important when implementing Kafka.

# 7. Domain Design Principles

The domain will follow the following principles:
- Each bounded context owns its business data
- Microservices must not access another microservice's database directly
- Business rules should reside within the domain and application layers rather than in controllers
- Communication between services will use REST or asynchronous events, depending on the user case
- Domain logic should remain independent of infrastructure technologies
- External systems such as databases, Kafka, and HTTP clients will be accessed through ports and adapters
- Historical business information must remain consistent even when related data changes in another context

The design will provide the foundation for implementating the microservices using Domain-Driven Design and Hexagonal Architecture