# Domain Design

This document defines the business domain of the e-commerce and order management platform. It describes the main business areas, their responsibilities, core domain entities, business rules, and the events that will be used to communicate relevant changes between microservices.

The purpose of this document is to establish a clear understanding of the business domain before implementing the application.

# Table of Contents

1. Business Overview
2. Main Business Flow
3. Bounded Contexts
4. Core Domain Entities
5. Aggregates
6. Value Objects
7. Order Lifecycle
8. Business Rules
9. Domain Events
10. Domain Design Principles
11. Open Questions

# 1. Business Overview

The platform is an e-commerce system focused on technology products.

Customers can browse the product catalog, add products to their shopping cart, place orders, and track their orders throughout their lifecycle.

Administrators can manage products, categories, inventory, and orders.

The platform is designed around independent business contexts, each represented by a dedicated microservice.

# 2. Main Business Flow

The main customer journey is:

```text
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
The Cart entity belongs to the Order Context.

## CartItem
- productId
- quantity
- ...

A CartItem represents a product and its selected quantity within a cart..
CartItem belongs to the Cart aggregate and cannot exist independently of its Cart.

## Order
- id
- userId
- status
- items
- total
- ...

The Order represents a confirmed purchase and its lifecycle
The Order entity belongs to the Order Context.

## OrderItem
- productId
- productName
- unitPrice
- quantity

An OrderItem represents a product inclded in an order.
The product name and unit price will be stored as part of the order so that historical orders remain consistent even if the original product is later modified.
OderItem belongs to the Order aggregate and cannot be modified independently of the Order.

## InventoryItem
- productId
- availableQuantity
- reservedQuantity

An inventoryItem represents the inventory information associated with a product.
The Inventory Service is the owner of this data.

# 5. Aggregates

Aggregates will be used to define consistency boundaries within the domain.

An aggregate is a group of related domain objects that must be treated as a single unit when enforcing business rules.

Each aggregate has an Aggregate Root, which is the only object through which the aggregate should be modified from outside.

## Order Aggregate
Order
└── OrderItem

Order id the Aggregate Root.

External components must interact with OrderItem through the Order rather than modifying an OrderItem directly

This allows the Order to enforce rules such as:

- An order must contain at least one item.
- An order total must be consistent with its items.
- Items cannot be modified once the order reaches a final state


## Cart Aggregate
Cart
└── CartItem

Cart is the Aggregate Root.

Cart items are managed through the Cart and should not be modified independently

## Inventory Aggregate

InventoryItem

For the initial design, InventoryItem will act as the Aggregate Root because inventory operations must be controlled through a single consistency boundary

The decision may be revisited if the inventory domain becomes more complex


# 6. Value Objects

The domain will also use Value Objects where they provide maningful business semantics.

Initial candidates include:

## Email
Represents a valid email address

Instead of treating an email as an arbitrary String, the domain can enforce the rules associated with a valid email address

## Money
Represents a monetary amount and its currency

Money
├── amount
└── currency

Using a Money Value Object prevents monetary concepts from being represented only by primitive values

## Address
Represents a customer's shipping or billing address

Address
├── street
├── city
├── postalCode
└── country

The exact fields will be defined when the order domain is implemented

## OrderStatus
The order status may also be represented as a domain-specific type rather than an arbitrary string.

Possible values include:
- PENDING
- PAID
- SHIPPED
- DELIVERED
- CANCELLED


# 7. Order Lifecycle

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


# 8. Business Rules

Business rules define constraints that must always be respected by the domain.

## Order Rules
- An order must contain at least one item
- An order total must equal the sum of its order items
- The price stored in an OrderItem represents the price at the time the order was created
- A PENDING order can be cancelled
- A PAID order may be cancelled according to the refund rules defined by the payment process
- A SHIPPED order cannot be cancelled through the standard cancellation flow
- A DELIVERED order cannot be cancelled

## Inventory Rules
- Available stock cannot be negative
- Reserved stock cannot exceed the available inventory
- Stock must be reserved before an order can be successfully completed
- Reserved stock must be released when an applicable order is cancelled
- Inventory modifications can only be performed by the Inventory Context.

## Product Rules
- A product must have a valid name
- A product must have a non-negative price
- A product must belong to a valid category
- Only authorized users can create, update, or delete products

The rules will later become candidates for unit and integration tests.

# 9. Domain Events

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

# 10. Domain Design Principles

The domain will follow the following principles:
- Each bounded context owns its business data
- Microservices must not access another microservice's database directly
- Business rules should reside within the domain and application layers rather than in controllers
- Communication between services will use REST or asynchronous events, depending on the user case
- Domain logic should remain independent of infrastructure technologies
- External systems such as databases, Kafka, and HTTP clients will be accessed through ports and adapters
- Aggregates will enforce consistency within their respective boundaries
- Historical business information must remain consistent even when related data changes in another context
- Domain entities should not depend directly on infrastructure frameworks whenever possible

The design will provide the foundation for implementating the microservices using Domain-Driven Design and Hexagonal Architecture


# 11. Open Questions

The following decisions will be addressed during the implementation:
- Should a paid order be cancellable
- How long can a PENDING order remain unpaid?
- Should inventory be reserved before or after payment?
- What should happen if payment succeeds but inventory reservation fails?
- What should happen if inventory is reserved but payment fails?
- How should failed Kafka messages be handled?
- Should the shopping cart be persisted permanently?
- Should payments eventually be represented by a dedicated Payment Service?
- Which order events should be exposed as Kafka integration events?