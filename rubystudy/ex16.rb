filename = ARGV.first

puts "We're going to erase #{filename}"
puts "If you don't want that, hit CTRL-C (^C)."
puts "If you do want that, hit RETURN."

$stdin.gets

puts "Opening the file..."
# open file for writing
target = open(filename, 'w')

puts "Truncating the file.  Goodbye!"
# empty the file
target.truncate(0)

puts "Now I'm going to ask you for three lines."
# create some text
print "line 1: "
line1 = $stdin.gets.chomp
print "line 2: "
line2 = $stdin.gets.chomp
print "line 3: "
line3 = $stdin.gets.chomp

puts "I'm going to write these to the file."
# white to the file
formatter = "%{first} %{second} %{third} %{fourth}"
target.write %{first: #{line1}, second: #{line2}, third: #{line3}}


puts "And finally, we close it."
# close file
target.close