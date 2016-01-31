def cheese_and_crackers(cheese_count, boxes_of_crackers)
  puts "You have #{cheese_count} cheeses!"
  puts "You have #{boxes_of_crackers} boxes of crackers!"
  puts "Man that's enough for a party!"
  puts "Get a blanket.\n"
end


puts "We can just give the function numbers directly:"
cheese_and_crackers(20, 30)


puts "OR, we can use variables from our script:"
amount_of_cheese = 10
amount_of_crackers = 50

cheese_and_crackers(amount_of_cheese, amount_of_crackers)


puts "We can even do math inside too:"
cheese_and_crackers(10 + 20, 5 + 6)


puts "And we can combine the two, variables and math:"
cheese_and_crackers(amount_of_cheese + 100, amount_of_crackers + 1000)

puts "my new own function"
def print_name(hello_word, print_name)
  puts "So we say #{hello_word} #{print_name}!"
end
print_name("hello", "Tanya")
print_name("hi", "Tanya")
print_name("goodmorning", "Tanya")
print_name("goodafternon", "Tanya")
print_name("goodevening", "Tanya")
print_name("goodnight", "Tanya")
print_name("bye", "Tanya")
print_name("bye again", "Tanya")
print_name("hi hi", "Tanya")
print_name("bye bye", "Tanya")
print_name("goodmorning dear", "Tanya")