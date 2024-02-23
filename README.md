# encrypt-decrypt

## Example run configurations

> java Main -mode enc -key 5 -data "Hello, World!" -alg shift // "Mjqqt1%\twqi"
>
> java Main -mode dec -key 5 -data "Mjqqt1%\twqi" -alg shift // "Hello, World!"

> java Main -mode enc -key 5 -data "Hello, World!" -alg unicode // "Mjqqt, Btwqi"
> 
> java Main -mode dec -key 5 -data "Hello, World!" -alg unicode // "Hello, World!"
> 
>java Main -mode enc -key 5 -in input.txt -out output.txt -alg shift
> 
>java Main -mode dec -key 5 -in output.txt -alg shift
