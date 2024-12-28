# capacitor-sec-display-plugin

To show secondary ui and exchange data with it

## Install

```bash
npm install capacitor-sec-display-plugin
npx cap sync
```

## API

<docgen-index>

* [`initialize()`](#initialize)
* [`showContent(...)`](#showcontent)
* [`sendMessage(...)`](#sendmessage)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### initialize()

```typescript
initialize() => Promise<void>
```

--------------------


### showContent(...)

```typescript
showContent(options: { url: string; }) => Promise<void>
```

| Param         | Type                          |
| ------------- | ----------------------------- |
| **`options`** | <code>{ url: string; }</code> |

--------------------


### sendMessage(...)

```typescript
sendMessage(options: { message: Record<string, any>; }) => Promise<void>
```

| Param         | Type                                                                       |
| ------------- | -------------------------------------------------------------------------- |
| **`options`** | <code>{ message: <a href="#record">Record</a>&lt;string, any&gt;; }</code> |

--------------------


### Type Aliases


#### Record

Construct a type with a set of properties K of type T

<code>{ [P in K]: T; }</code>

</docgen-api>
